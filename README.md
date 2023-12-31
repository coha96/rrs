# 간단한 맛집 리뷰 서비스 
### 토이토이 프로젝트!
스프링 부트와 JPA로 간단한 RESTful 백엔드 애플리케이션 만들기를 진행한다.  
개인적인 연습용으로 진행하는 프로젝트로 토이*2 프로젝트 정도라고 명하겠다. 조금 간단하다는 점 참고바랍니다.    

#### 🚩 핵심
1. 스프링 프레임워크 개발  
2. Git을 통한 버전관리  
3. JPA와 QueryDSL 사용  
4. 도커와 AWS를 이용한 배포

---
### 💡기획자 요청사항
안녕하세요! 저희는 지금부터 맛집 리뷰 서비스를 만들어볼 예정입니다.  
저희 서비스는 맛집을 관리하고 사용자들이 맛집에 방문한 이후에 리뷰를 작성해서 다른 사용자들이 맛집을 방문하기전 리뷰와 평균 별점을 확인할 수 있도록 하는 서비스에요!  
저희 서비스에서는 맛집을 등록하고 수정하고 삭제할 수 있으며, 맛집마다 리뷰를 작성하거나 삭제할 수 있습니다.  
맛집은 이름(매장명), 주소, 메뉴를 가지고 있으며 메뉴는 이름과 가격으로 구성됩니다.  
리뷰는 본문과 별점을 가지고 있습니다. 맛집은 여러개의 메뉴와 리뷰를 가질 수 있어요! 리뷰는 메뉴별로는 작성이 불가합니다!
  
### ❓요구사항 분석하기  
안녕하세요! 저희는 지금부터 **맛집 리뷰 서비스**를 만들어볼 예정입니다.  
저희 서비스는 **맛집을 관리**하고 사용자들이 맛집에 방문한 이후에 리뷰를 작성해서 다른 사용자들이 맛집을 방문하기전 **리뷰와 평균 별점을 확인**할 수 있도록 하는 서비스에요!  
저희 서비스에서는 **맛집을 등록하고 수정하고 삭제**할 수 있으며, 맛집마다 **리뷰를 작성하거나 삭제**할 수 있습니다.  
**맛집은 이름(매장명), 주소, 메뉴**를 가지고 있으며 **메뉴는 이름과 가격**으로 구성됩니다.  
**리뷰는 본문과 별점**을 가지고 있습니다. **맛집은 여러개의 메뉴와 리뷰**를 가질 수 있어요! **리뷰는 메뉴별로는 작성이 불가**합니다!

### **사용자 Flow (Usecase)**

- 맛집을 등록할 수 있다
- 맛집을 수정할 수 있다
- 맛집을 삭제할 수 있다
- 맛집에 리뷰를 작성할 수 있다
- 맛집에 작성한 리뷰를 삭제할 수 있다
- 맛집에 작성된 리뷰와 평균별점을 확인할 수 있다

### **데이터**

- 맛집
    - 이름 (String)
    - 주소 (String)
    - N개의 메뉴
        - 이름 (String)
        - 가격 (Number)
    - N개의 리뷰
        - 본문 (String)
        - 별점 (Number)

### ERD  
![image](https://github.com/coha96/rrs/assets/126804445/71fd9354-94cd-4e56-87c0-e0400578462b)

mrs.restaurant → timestamp 
: 실제 자바 데이터 타입에서는 LocalDateTime 또는 ZoneDateTime으로 만들어주면 된다.

<br></br>

### API 스펙
- 아래 클래스 및 인터페이스는 해당 프로젝트 진행 전 Test 코드입니다.  
![image](https://github.com/coha96/rrs/assets/126804445/f669f586-1e95-4cee-9400-ae83c51a4d7b)

#### 맛집 리스트 가져오기 API
```java
GET /restaurants

// response
[
  {
    "id": Long,
    "name": string,
    "address": string,
    "createdAt": string,
    "updatedAt": string
  },
  ...
}
```
#### 맛집 정보 가져오기 API
```java
GET /restaurant/{restaurantId}

// response
{
  "id": Long,
  "name": string,
  "address": string,
  "createdAt": string,
  "updatedAt": string,
  "menus": [
    {"id": Long, "name": string, "price": int, "createdAt": string, "updatedAt": string},
    {"id": Long, "name": string, "price": int, "createdAt": string, "updatedAt": string},
    ...
  ]
}
```
#### 맛집 생성 API
```java
POST /restaurant
{
  "name": string,
  "address": string,
  "menus": [
    {"name": string, "price": int},
    ...
  ]
}
```

#### 맛집 수정 API
```java
PUT /restaurant/{restaurantId}
{
  "name": string,
  "address": string,
  "menus": [
    {"name": string, "price": int},
    ...
  ]
}
```

#### 맛집 삭제 API
```java
DELETE /restaurant/{restaurantId}
```

#### 리뷰 작성 API
```java
POST /review
{
  "restaurantId": int,
  "content": string,
  "score": float
}
```

#### 리뷰 삭제 API
```java
DELETE /review/{reviewId}
```

#### 맛집에 등록된 리뷰 가져오기 API
```java
GET /restaurant/{restaurantId}/reviews

// response
{
  "avgScore": float, // 평균 별점
  "reviews": [
    {"id": int, "content": string, "score": float, "createdAt": string},
    {"id": int, "content": string, "score": float, "createdAt": string},
    {"id": int, "content": string, "score": float, "createdAt": string}
  ],
  "page": {
    "offset": int,
    "limit": int
  }
}
```
<br></br>
<br></br>


