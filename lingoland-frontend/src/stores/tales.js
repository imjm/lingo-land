import { ref, computed, inject } from "vue";
import { defineStore } from "pinia";
import swal from "sweetalert2";
import { useRouter } from "vue-router";
import { httpStatus } from "@/apis/http-status";

export const useTaleStore = defineStore("tale", () => {
    const selectedtale = ref(null);
    const tales = ref([]) //전체 tales리스트
    const tale = ref([]) // tale 하나에 들어있는 리스트
    const axios = inject("axios");
    const router = useRouter();
    window.Swal = swal;

    //마이페이지에서 클릭했을 때
    const myTalesList = function() {
        //내 tales list 불러오기
        axios.get('/fairy-tales/members').then((response) => {
            tales.value=response.data
        }) .then(()=> {
            router.push({name : "bookList"})
        })
        
    }

    //다른 사람 프로필로 클릭했을 때
    const otherTalesList = function(userId) {
        //다른사람
        axios.get(`/fairy-tales/members/${userId}`).then((response) => {
            tales.value=response.data
        })
    }

    //동화 1개 내용전부 부르기
    const oneTaleById = function(bookId) {
        axios.get(`/fairy-tales/${bookId}`).then((response) => {
            tale.value=response.datae
            console.log('동화받아왔음')       
            console.log(response.data)
        }).then(()=> {
            router.push({ name: "bookDetail", params: { bookId: bookId } });
        })
    }


    tale.value = [

        {
            id:1,
            title:'글 1',
            source :"https://avatars0.githubusercontent.com/u/9064066?v=4&s=460", 
            text : '내용 1',
        },
        {
            id:2,
            title:'글 2',
            source :"https://avatars0.githubusercontent.com/u/9064066?v=4&s=460", 
            text : '내용 2',
        },
        {
            id:3,
            title:'글 3',
            source :"https://avatars0.githubusercontent.com/u/9064066?v=4&s=460", 
            text : '내용 3',
        },
        {
            id:4,
            title:'글 4',
            source :"https://avatars0.githubusercontent.com/u/9064066?v=4&s=460", 
            text : '내용 4',
        },

    ]

    //임시 데이터
    
    tales.value =  [
        {
            image: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
            title: "나의 라임 주스",
            description:
                "김초딩은 책꽂이에 꽂혀 있는 나의 라임 오렌지 나무라는 책을 보고는 생각했다. ‘라임이면 라임이고 오렌지면 오렌지지 라임 오렌지는 대체 뭐란 말인가?’ 그런 의문을 가지고 김초딩은 라임과 오렌지 각각으로 주스를 만들어 먹기로.......",
            allowed: "나만보기",
            id:1,
        },
        {
            image: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
            title: "여름은 역시 더워",
            description:
                "더운 여름 체육 수업을 끝내고 돌아온 박초딩은 돌연 반의 에어컨이 너무 넙다고 생각하게 된다. 이에 대해 다른 반을 방문해보며 확인해 본 결과 다른 반 모두 전체적으로 덥다는 것을 알게되고, 이런 갑작스러운 에어컨 오작동(?)의 원인을 파악하기 위해 주변 조사를 시작하는데...",
            allowed: "그룹원만 보기",
            id:2
        },
        {
            image: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
            title: "엄마 용돈이 부족해요",
            description:
                "이초딩은 용돈을 지난주에 받았지만 다른 친구들이 받는 금액에 비해 월등히 적은 금액으로 금방 소진해 버렸다. 하지만 엄마에게 용돈이 부족하다고 말을 하면 화만 내시는데, 이 문제를 어떻게 해결해야 할지 고민해야했다. 그러던 어느 날, 엄마는 외출하셨는데 식탁위에 두툼한 지갑만 남아있는데....",
            allowed: "전체 공개",
            id:3
        },
    ];

   

    return { oneTaleById ,myTalesList, otherTalesList,  selectedtale, tales, tale };
});
