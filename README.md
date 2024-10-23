# INCER PAY

## 온라인 결제 게이트웨이(PG; Payment Gateway) 서비스

<img width="988" alt="image" src="https://github.com/user-attachments/assets/17d0e5ee-dfa1-42cb-95b0-477665f32838">

-----

* `상점의 결제 시스템을 대리해 VAN / 카드사 등과 거래를 중개`
* `사용자와 결제 데이터를 ADMIN 페이지에서 조회 및 시스템 상태를 모니터링`

## Using Stack

- Language: Java
- JVM: corretto-21
- Spring Boot 3.2
- Persistence: Spring Data JPA / QueryDSL
- DB: MySQL 8
- TEST: JUnit5


## API Request URL

- 결제 SDK : [URL](http://ec2-43-202-59-167.ap-northeast-2.compute.amazonaws.com:5555)
- 상점 ADMIN : 로그인 만들어지지 않음

## API SWAGGER / Repository

> 기본 데이터 테스트를 위한 포트 노출

- 결제 게이트웨이 : [URL](http://ec2-43-202-59-167.ap-northeast-2.compute.amazonaws.com:5555)
- 결제 : [URL](http://ec2-43-202-59-167.ap-northeast-2.compute.amazonaws.com:2222)
- 회원 : [URL](http://ec2-43-202-59-167.ap-northeast-2.compute.amazonaws.com:1111)
- 상점 : [URL](http://ec2-43-202-59-167.ap-northeast-2.compute.amazonaws.com:3333)
- [외부 결제 Mocking용 API 서버](https://github.com/kail-is/simple-payment-api) : [URL](http://43.202.59.167:1234/swagger-ui/index.html)


> 배포 정상화 후 URL 재첨부 예정

## 로컬 구동

- docker-compose 세팅

> 수정 중

## 개발 요구사항 및 진척도 / 누락 부분 정리


### 요구사항

- **3조 BE**

- [x] 다양한 결제 수단을 지원하는 결제 처리 기능을 구현한다. (현재 CARD만 가능하나, 확장성 있게 구현 완료)
- [x] 거래 승인, 취소, 환불 등의 거래 상태를 실시간으로 관리하고 추적할 수 있어야 한다.

* [진행 중 내용](https://github.com/FC-InnerCircle/icd01-team04-fintech2-be/issues/7)
- [ ] 고가용성 및 장애 복구: 24/7 복구할 수 있는 아키텍처 - 이중화(HA) 및 장애 조치(failover) 
- [ ] 결제 실패 및 오류 처리: 다양한 실패 시나리오 처리, 재시도 로직으로 사용자 경험 개선 : 진행 중
- [ ] RESTful API 및 다양한 프로그래밍 언어에 대한 SDK를 제공 : 시간이 된다면 진행 욕심



- **4조 BE**

- [ ] 상점 계정 및 관리자 계정에 대한 로그인 / 인증 기능을 제공해야 한다.
- [x] 상점 계정 및 관리자 계정에 대한 권한 관리 기능을 제공해야 한다.
- [x] API 키 발급 / 관리 기능 제공
- [x] 상점과 관리자는 결제 내역을 조회할 수 있어야 한다.
- [ ] 상점과 관리자는 다양한 통계 자료를 확인할 수 있는 리포팅 기능을 제공받아야 한다.
- [ ] 테스트용 결제 수단을 생성하고 관리할 수 있어야 한다.




 
