package com.ssafy.a603.lingoland.group.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.JoinGroupRequestDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.entity.GroupMember;
import com.ssafy.a603.lingoland.group.entity.GroupMemberId;
import com.ssafy.a603.lingoland.group.repository.GroupMemberRepository;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.util.ImgUtils;

@ContextConfiguration(classes = {GroupServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GroupServiceImplDiffblueTest {
    @MockBean
    private GroupMemberRepository groupMemberRepository;

    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @MockBean
    private ImgUtils imgUtils;

    @MockBean
    private MemberRepository memberRepository;

	/**
     * Method under test:
     * {@link GroupServiceImpl#create(CreateGroupDTO, MultipartFile, CustomUserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate4() throws IOException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.ssafy.a603.lingoland.group.dto.CreateGroupDTO.name()" because "request" is null
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.create(GroupServiceImpl.java:39)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        when(groupRepository.save(Mockito.<Group>any())).thenReturn(buildResult);

        Member member = new Member();
        member.updateNickname("Nickname");
        member.updateRefreshToken("Refresh");
        Optional<Member> ofResult = Optional.of(member);
        when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult);
        GroupMember.GroupMemberBuilder descriptionResult = GroupMember.builder()
                .description("The characteristics of someone or something");
        GroupMemberId id = GroupMemberId.builder().groupId(1).memberId(1).build();
        GroupMember buildResult2 = descriptionResult.id(id).build();
        when(groupMemberRepository.save(Mockito.<GroupMember>any())).thenReturn(buildResult2);
        when(imgUtils.saveImage(Mockito.<MultipartFile>any(), Mockito.<String>any())).thenReturn("Save Image");
        MockMultipartFile groupImage = new MockMultipartFile("Name",
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        // Act
        groupServiceImpl.create(null, groupImage, new CustomUserDetails(new Member()));
    }

    /**
     * Method under test: {@link GroupServiceImpl#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<Group> groupList = new ArrayList<>();
        when(groupRepository.findAll()).thenReturn(groupList);

        // Act
        List<Group> actualFindAllResult = groupServiceImpl.findAll();

        // Assert
        verify(groupRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(groupList, actualFindAllResult);
    }

    /**
     * Method under test: {@link GroupServiceImpl#findAll()}
     */
    @Test
    void testFindAll2() {
        // Arrange
        when(groupRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groupServiceImpl.findAll());
        verify(groupRepository).findAll();
    }

    /**
     * Method under test: {@link GroupServiceImpl#findById(int)}
     */
    @Test
    void testFindById() {
        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        Optional<Group> ofResult = Optional.of(buildResult);
        when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        Group actualFindByIdResult = groupServiceImpl.findById(1);

        // Assert
        verify(groupRepository).findById(eq(1));
        assertEquals("Group Image", actualFindByIdResult.getGroupImage());
        assertEquals("Name", actualFindByIdResult.getName());
        assertEquals("The characteristics of someone or something", actualFindByIdResult.getDescription());
        assertNull(actualFindByIdResult.getId());
        assertNull(actualFindByIdResult.getDeletedAt());
        assertEquals(0, actualFindByIdResult.getMemberCount());
        assertEquals(1, actualFindByIdResult.getPassword().intValue());
        assertFalse(actualFindByIdResult.isDeleted());
        List<GroupMember> groupMembers = actualFindByIdResult.getGroupMembers();
        assertTrue(groupMembers.isEmpty());
        assertEquals(groupMembers, groupServiceImpl.findAll());
        assertSame(leader, actualFindByIdResult.getLeader());
    }

    /**
     * Method under test: {@link GroupServiceImpl#findById(int)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<Group> emptyResult = Optional.empty();
        when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> groupServiceImpl.findById(1));
        verify(groupRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link GroupServiceImpl#findById(int)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(groupRepository.findById(Mockito.<Integer>any())).thenThrow(new RuntimeException("No such group"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groupServiceImpl.findById(1));
        verify(groupRepository).findById(eq(1));
    }

	/**
     * Method under test:
     * {@link GroupServiceImpl#update(Integer, UpdateGroupDTO, MultipartFile, CustomUserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate4() throws IOException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.ssafy.a603.lingoland.member.entity.Member.getLoginId()" because "<local2>.member" is null
        //       at com.ssafy.a603.lingoland.member.security.CustomUserDetails.getUsername(CustomUserDetails.java:35)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.getMemberFromUserDetails(GroupServiceImpl.java:167)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.update(GroupServiceImpl.java:69)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        Optional<Group> ofResult = Optional.of(buildResult);
        when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Member member = new Member();
        member.updateNickname("Nickname");
        member.updateRefreshToken("Refresh");
        Optional<Member> ofResult2 = Optional.of(member);
        when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult2);
        when(imgUtils.saveImage(Mockito.<MultipartFile>any(), Mockito.<String>any())).thenReturn("Save Image");
        doNothing().when(imgUtils).deleteImage(Mockito.<String>any(), Mockito.<String>any());
        UpdateGroupDTO request = new UpdateGroupDTO(1, "Name", 1, "The characteristics of someone or something");

        MockMultipartFile groupImage = new MockMultipartFile("Name",
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        // Act
        groupServiceImpl.update(1, request, groupImage, new CustomUserDetails(null));
    }

	/**
     * Method under test:
     * {@link GroupServiceImpl#deleteById(int, CustomUserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteById4() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.ssafy.a603.lingoland.member.entity.Member.getLoginId()" because "<local2>.member" is null
        //       at com.ssafy.a603.lingoland.member.security.CustomUserDetails.getUsername(CustomUserDetails.java:35)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.getMemberFromUserDetails(GroupServiceImpl.java:167)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.deleteById(GroupServiceImpl.java:83)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        Optional<Group> ofResult = Optional.of(buildResult);
        when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Member member = new Member();
        member.updateNickname("Nickname");
        member.updateRefreshToken("Refresh");
        Optional<Member> ofResult2 = Optional.of(member);
        when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult2);

        // Act
        groupServiceImpl.deleteById(1, new CustomUserDetails(null));
    }

	/**
     * Method under test:
     * {@link GroupServiceImpl#addMemberToGroupWithPasswordCheck(int, JoinGroupRequestDTO, CustomUserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddMemberToGroupWithPasswordCheck4() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.ssafy.a603.lingoland.member.entity.Member.getLoginId()" because "this.member" is null
        //       at com.ssafy.a603.lingoland.member.security.CustomUserDetails.getUsername(CustomUserDetails.java:35)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.getMemberFromUserDetails(GroupServiceImpl.java:167)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.addMemberToGroupWithPasswordCheck(GroupServiceImpl.java:100)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        Optional<Group> ofResult = Optional.of(buildResult);
        when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Member member = new Member();
        member.updateNickname("Nickname");
        member.updateRefreshToken("Refresh");
        Optional<Member> ofResult2 = Optional.of(member);
        when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult2);
        GroupMember.GroupMemberBuilder descriptionResult = GroupMember.builder()
                .description("The characteristics of someone or something");
        GroupMemberId id = GroupMemberId.builder().groupId(1).memberId(1).build();
        GroupMember buildResult2 = descriptionResult.id(id).build();
        when(groupMemberRepository.save(Mockito.<GroupMember>any())).thenReturn(buildResult2);
        JoinGroupRequestDTO joinGroupRequestDTO = new JoinGroupRequestDTO("The characteristics of someone or something", 1);

        // Act
        groupServiceImpl.addMemberToGroupWithPasswordCheck(1, joinGroupRequestDTO, new CustomUserDetails(null));
    }

	/**
     * Method under test:
     * {@link GroupServiceImpl#findAllMembersByGroupId(int, String, CustomUserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindAllMembersByGroupId() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.ssafy.a603.lingoland.member.entity.Member.getLoginId()" because "<local2>.member" is null
        //       at com.ssafy.a603.lingoland.member.security.CustomUserDetails.getUsername(CustomUserDetails.java:35)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.getMemberFromUserDetails(GroupServiceImpl.java:167)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.findAllMembersByGroupId(GroupServiceImpl.java:109)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Member member = new Member();
        member.updateNickname("Nickname");
        member.updateRefreshToken("Refresh");
        Optional<Member> ofResult = Optional.of(member);
        when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        groupServiceImpl.findAllMembersByGroupId(1, "Keyword", new CustomUserDetails(null));
    }

    /**
     * Method under test:
     * {@link GroupServiceImpl#removeMemberFromGroup(int, CustomUserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRemoveMemberFromGroup() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.ssafy.a603.lingoland.member.entity.Member.getLoginId()" because "<local2>.member" is null
        //       at com.ssafy.a603.lingoland.member.security.CustomUserDetails.getUsername(CustomUserDetails.java:35)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.getMemberFromUserDetails(GroupServiceImpl.java:167)
        //       at com.ssafy.a603.lingoland.group.service.GroupServiceImpl.removeMemberFromGroup(GroupServiceImpl.java:129)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        Optional<Group> ofResult = Optional.of(buildResult);
        when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Member member = new Member();
        member.updateNickname("Nickname");
        member.updateRefreshToken("Refresh");
        Optional<Member> ofResult2 = Optional.of(member);
        when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult2);
        GroupMember.GroupMemberBuilder descriptionResult = GroupMember.builder()
                .description("The characteristics of someone or something");
        GroupMemberId id = GroupMemberId.builder().groupId(1).memberId(1).build();
        GroupMember buildResult2 = descriptionResult.id(id).build();
        Optional<GroupMember> ofResult3 = Optional.of(buildResult2);
        when(groupMemberRepository.findById(Mockito.<GroupMemberId>any())).thenReturn(ofResult3);

        // Act
        groupServiceImpl.removeMemberFromGroup(1, new CustomUserDetails(null));
    }

    /**
     * Method under test: {@link GroupServiceImpl#save(Group)}
     */
    @Test
    void testSave() {
        // Arrange
        Member leader = new Member();
        leader.updateNickname("Nickname");
        leader.updateRefreshToken("Refresh");
        Group buildResult = Group.builder()
                .description("The characteristics of someone or something")
                .groupImage("Group Image")
                .leader(leader)
                .name("Name")
                .password(1)
                .build();
        when(groupRepository.save(Mockito.<Group>any())).thenReturn(buildResult);

        // Act
        Group actualSaveResult = groupServiceImpl.save(null);

        // Assert
        verify(groupRepository).save(isNull());
        assertEquals("Group Image", actualSaveResult.getGroupImage());
        assertEquals("Name", actualSaveResult.getName());
        assertEquals("The characteristics of someone or something", actualSaveResult.getDescription());
        assertNull(actualSaveResult.getId());
        assertNull(actualSaveResult.getDeletedAt());
        assertEquals(0, actualSaveResult.getMemberCount());
        assertEquals(1, actualSaveResult.getPassword().intValue());
        assertFalse(actualSaveResult.isDeleted());
        List<GroupMember> groupMembers = actualSaveResult.getGroupMembers();
        assertTrue(groupMembers.isEmpty());
        assertEquals(groupMembers, groupServiceImpl.findAll());
        assertSame(leader, actualSaveResult.getLeader());
    }

    /**
     * Method under test: {@link GroupServiceImpl#save(Group)}
     */
    @Test
    void testSave2() {
        // Arrange
        when(groupRepository.save(Mockito.<Group>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groupServiceImpl.save(null));
        verify(groupRepository).save(isNull());
    }

}
