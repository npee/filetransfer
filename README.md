# Server for project
## NAS
- https://velog.io/@pdot715/Ubuntu-Server-NAS-%EA%B5%AC%EC%B6%95%EA%B8%B0
## Socket을 이용하여 Python Server, Java Client 통신
- Java는 거의 Server용으로 사용되기 때문에, Java Server, Python Client의 경우는 많이 있어도 반대의 경우를 찾기 어려움
## 2020.01.17 새벽
- FTP Server 구축
- 하다보니 FTP는 단순히 파일 전송밖에 안될 것 같다
- 공유기 업데이트했는데 무선 인터넷이 잡히지 않는다 
## 2020.01.17 저녁
- 다시 socket으로 돌아옴
- tornado로 socket 통신 시도중
## 2020.01.18 오후
- Websocket 사용하려고 하니 아래 오류가 계속 떠서 처리중
- Can "Upgrade" only to "WebSocket".
## 2020.01.20
- Spring websocket client 구현중
## 2020.01.21
- 18일의 메시지는 오류 메시지가 아니었던 것 같다
- Spring websocket client와 Tornado websocket Server 연결
- Eclipse console에 메시지 작성하면 server에서 가공하여 되돌려준다
- 이미지 처리는 어떻게 해야하는가
## 2020.01.22
- message와 message 전송 대신 파일 전송하는 방법을 찾아야 한다
- sendBinary() 메서드 실험 필요함
- multipart/form-data에서 binary로 변환이 가능한지 확인 필요
## 2020.01.23~25
- 설연휴로 쉬는중
## 2020.01.26
- 22일날 예정이었던것 실험하기
## 2020.01.26~2020.01.27
- IO 관련 클래스. 메서드들에 대하여 제대로 이해해야 한다
- WebSocket 실험하기 전에 기존 IO 방법도 해보도록 하자
- 결국 27일도 바빠서 해보지못했다
## 2020.01.28
- 일반 Socket을 사용하여 파일 주고받기 구현(Java Client, Python Server)
***
## 2020.01.30~
- local 상에서의 파일 전송은 성
- local 상에서 DB 연결 해야함(MySQL)
- DB연결까지 확인 한 후 WebServer에 연동해보자
