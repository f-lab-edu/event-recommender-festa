# :ferris_wheel: FESTA! :ferris_wheel:

사용자가 설정한 지역의 이벤트와 행사를 추천해주는 서비스 입니다.    
백엔드 로직에 집중하기 위해서 화면은 프로토타입으로 설계하였으며 
REST API 서버로 대용량 트래픽을 고려한 애플리케이션으로 개발하였습니다. 
화면 디자인이나 Usecase는 [WIKI](https://github.com/f-lab-edu/event-recommender-festa/wiki) 에서 확인이 가능합니다.



<br>
<br>

##  :rocket: 프로젝트 사용기술 
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle)
- [Java 8](https://docs.oracle.com/javase/8/docs/api/)
- [MySQL](https://dev.mysql.com/doc/refman/8.0/en/)
- [Mybatis](https://mybatis.org/mybatis-3/)
- [Maven](http://maven.apache.org/guides/index.html)
- [Redis](https://redis.io/documentation)
- [Naver cloud platform](https://docs.ncloud.com/ko/)
- [AWS S3](https://docs.aws.amazon.com/index.html?nc2=h_ql_doc_do_v)
- [Firebase](https://firebase.google.com/docs)
- [Docker](https://www.docker.com/)
- intellij IDEA

<br>
<br>

##  :rocket: 전체 프로젝트의 구조

![image](https://user-images.githubusercontent.com/58355531/109328559-e8bf1400-789c-11eb-8a47-5db63e8d2c3d.png)

<br>
<br>

##  :rocket: WIKI

화면설계에 대한 **kakao oven** 프로토타입 디자인과 Usecase를 보실 수 있습니다.
API에 대한 상세하게 설명해두었으며 기술적인 문제에 부딪혀 해결한 이야기에 대한 개인 테크블로그의 주소도 포함되어있습니다.

 - click! :arrow_forward: [Usecase](https://github.com/f-lab-edu/event-recommender-festa/wiki/Usecase)
 - click! :arrow_forward: [WIKI Home](https://github.com/f-lab-edu/event-recommender-festa/wiki)
 - click! :arrow_forward: [이슈해결을 정리한 테크블로그 Posts by 이지은(Jane)](https://github.com/f-lab-edu/event-recommender-festa/wiki/%EC%9D%B4%EC%8A%88%ED%95%B4%EA%B2%B0-%EB%B8%94%EB%A1%9C%EA%B7%B8-Posts-by-%EC%9D%B4%EC%A7%80%EC%9D%80(Jane))
 - click! :arrow_forward: [이슈해결을 정리한 테크블로그 Posts by 최윤정(Yo0oN)](https://github.com/f-lab-edu/event-recommender-festa/wiki/%EC%9D%B4%EC%8A%88%ED%95%B4%EA%B2%B0-%EB%B8%94%EB%A1%9C%EA%B7%B8-Posts-by-%EC%B5%9C%EC%9C%A4%EC%A0%95(Yo0oN))
 - click! :arrow_forward: [API 상세스펙](https://github.com/f-lab-edu/event-recommender-festa/wiki#%EC%83%81%EC%84%B8-api-%EC%8A%A4%ED%8E%99-%EB%B0%94%EB%A1%9C%EA%B0%80%EA%B8%B0)
 - click! :arrow_forward: [PR규칙](https://github.com/f-lab-edu/event-recommender-festa/wiki#pr-%EA%B7%9C%EC%B9%99)
 
<br>
<br>

##  :rocket: 프로젝트 주요 관심사

:heavy_check_mark: 대용량 트래픽의 상황에서 지속적인 서버 성능을 개선하기 위해 노력하였습니다.    
:heavy_check_mark: 클린코드를 위한 꾸준한 코드 리팩토링을 진행 중입니다.      
:heavy_check_mark: 이유와 근거가 명확한 기술의 사용을 지향합니다.    
:heavy_check_mark: 객체지향적 개념을 이해하고 이를 코드에 녹여내어 의미있는 설계를 지향하였습니다.    
:heavy_check_mark: 성공만 하는 테스트 보단 실패할 만한 단위 테스트를 작성하였습니다.    
:heavy_check_mark: 반복적인 작업은 자동화하여 개발의 효율을 높이기 위해 노력하였습니다.      

<br>
<br>


### :diamond_shape_with_a_dot_inside: Git-Flow 브랜치 전략

**Git-Flow 브랜치 전략**에 따라 기능별로 브랜치를 나누어 작업하고 있고 
모든 브랜치에 대해 pull request를 통한 리뷰 완료 후 Merge를 하고 있습니다.

<br>

![gitflow](https://user-images.githubusercontent.com/58355531/94992578-f14d4000-05c5-11eb-9ab1-527abc06d8b2.PNG)
> 출처: https://nvie.com/posts/a-successful-git-branching-model/

<br>
<br>

:white_check_mark: master : 제품으로 출시될 수 있는 브랜치를 의미합니다.     
:white_check_mark: develop : 다음 출시 버전을 개발하는 브랜치입니다. feature에서 리뷰완료한 브랜치를 Merge하고 있습니다.    
:white_check_mark: feature : 기능을 개발하는 브랜치    
:white_check_mark: release : 이번 출시 버전을 준비하는 브랜치    
:white_check_mark: hotfix : 출시 버전에서 발생한 버그를 수정하는 브랜치    

<br>

#### 참고문헌
- 우아한 형제들 기술블로그 "우린 Git-flow를 사용하고 있어요"   
<https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html>

<br>
<br>

### :diamond_shape_with_a_dot_inside: Jenkins CI/CD

빌드와 테스트를 자동화 하여 개발 효율성을 높일 수 있도록 젠킨스를 활용하였습니다. 
아래의 주소를 통해 젠킨스 status 확인이 가능합니다.

:heavy_check_mark: 젠킨스 주소 바로가기 : <http://34.64.219.27:8081/>


<br>
<br>

### :diamond_shape_with_a_dot_inside: PR 규칙

- 신규개발 건은 `develop` 을 base로 `feature/#이슈번호` 의 브랜치명으로 생성 후 작업한 다음 PR을 날립니다.
- 아직 개발 진행 중이라면 `In Progress` 라벨을 달고, 코드리뷰가 필요한 경우 `Asking for Review` 라벨을 답니다. 리뷰 후 리팩토링이 필요하다면 추가로 `refactoring` 라벨을 달아 진행합니다.
- 모든 PR은 반드시 지정한 리뷰어에게 코드리뷰를 받아야만 합니다. 
- 리뷰어 중 1명 이상의 `Approve` 를 받아야 `Merge pull request` 를 할 수 있습니다.
- `commit` 을 할 때마다 Jenkins CI가 자동으로 실행되며 단위테스트, 통합테스트에 모두 통과되어야 Merge pull request가 가능합니다.

<br>
<br>

##  :rocket: 화면 구성도

![image](https://user-images.githubusercontent.com/58355531/109332890-15c1f580-78a2-11eb-9596-eadf94a1ac10.png)

<br>

___


![image](https://user-images.githubusercontent.com/58355531/109332798-f6c36380-78a1-11eb-9283-92796a76ade2.png)

<br>
<br>

##  :rocket: DB ERD 구조

![event-recommender-festa-erd](https://user-images.githubusercontent.com/53729311/104211019-e896c080-5476-11eb-8dbc-183656873e5e.jpg)

[DB 테이블 설계:  ](https://docs.google.com/spreadsheets/d/19-vpLojODE6La68Jk_XspwSI7-4wW4TMxH7wVmMMXQU/edit?usp=sharing)<https://docs.google.com/spreadsheets/d/19-vpLojODE6La68Jk_XspwSI7-4wW4TMxH7wVmMMXQU/edit?usp=sharing>

<br>
<br>

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Ff-lab-edu%2Fevent-recommender-festa&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
