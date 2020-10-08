# FESTA! 

사용자가 설정한 지역의 이벤트와 행사를 추천해주는 서비스 입니다.    
백엔드 로직에 집중하기 위해서 화면은 프로토타입으로 설계하였으며 
REST API 서버로 대용량 트래픽을 고려한 애플리케이션으로 개발하였습니다. 
화면 디자인이나 Usecase는 WIKI에서 확인이 가능합니다.

<br>
<br>

## 프로젝트 사용기술 
- Spring Boot
- Java 8
- MySQL
- Mybatis
- Maven
- [Redis](https://redis.io/documentation)
- Naver cloud platform 
- intellij IDEA

<br>
<br>

## 전체 프로젝트의 구조

<br>
<br>


## 프로젝트 주요 관심사

- 대용량 트래픽의 상황에서 지속적인 서버 성능 개선
- 클린코드를 위한 꾸준한 코드 리팩토링 
- 이유와 근거가 명확한 기술의 사용
- 객체지향적 개념을 이해하고 이를 코드에 녹여내어 의미있는 설계를 지향

<br>
<br>


### Git-Flow 브랜치 전략

**Git-Flow 브랜치 전략**에 따라 기능별로 브랜치를 나누어 작업하고 있고 
모든 브랜치에 대해 pull request를 통한 리뷰 완료 후 Merge를 하고 있습니다.

<br>

![gitflow](https://user-images.githubusercontent.com/58355531/94992578-f14d4000-05c5-11eb-9ab1-527abc06d8b2.PNG)
> 출처: https://nvie.com/posts/a-successful-git-branching-model/

<br>
<br>

- master : 제품으로 출시될 수 있는 브랜치를 의미합니다. 
- develop : 다음 출시 버전을 개발하는 브랜치입니다. feature에서 리뷰완료한 브랜치를 Merge하고 있습니다.
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정하는 브랜치

<br>

#### 참고문헌
- 우아한 형제들 기술블로그 "우린 Git-flow를 사용하고 있어요"   
<https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html>

<br>
<br>

## WIKI

화면설계에 대한 **kakao oven** 프로토타입 디자인과 Usecase를 보실 수 있습니다.
또한, 기술적인 문제에 부딪혀 해결한 이야기에 대한 개인 테크블로그의 주소도 포함되어있습니다.

 - [Usecase](https://github.com/f-lab-edu/event-recommender-festa/wiki/Usecase)
 - [WIKI Home](https://github.com/f-lab-edu/event-recommender-festa/wiki)
 - [이슈해결을 정리한 테크블로그](https://github.com/f-lab-edu/event-recommender-festa/wiki/%EC%9D%B4%EC%8A%88%ED%95%B4%EA%B2%B0Posts)
 
<br>
<br>

## 화면 구성도

![사용자화면구성도 (1)](https://user-images.githubusercontent.com/58355531/93668916-f5a83200-faca-11ea-87ac-e72e55daffa5.png)

<br>

___


![주최자화면구성도](https://user-images.githubusercontent.com/58355531/93669060-2472d800-facc-11ea-977d-fc679c0519f0.png)

<br>
<br>

## DB ERD 구조

<br>
<br>

