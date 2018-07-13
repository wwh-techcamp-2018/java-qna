## Todo List
- 답변 추가하기
    - 사용자는 질문 상세보기 화면에서 답변 목록을 볼 수 있다.
    O
    - 로그인한 사용자는 답변을 추가할 수 있다.
    O
    - 자신이 쓴 답변은 삭제할 수 있다.
    O
    
- 답변 삭제하기
    - 자신이 쓴 답변에 한해 삭제할 수 있다.
    O
    - 답변은 질문에 종속 되기 때문에 URL 매핑은 다음과 같이
        "/questions/{questionId}/answers/{id}" + @DeleteMapping
        PUT 메소드 처럼 HTML에서 꼼수써서 요청 보낼 수 있음
        O
///////////////////////        
- 질문 삭제하기
    - 질문 데이터를 완전히 삭제하지말고 삭제 상태(deleted - boolean type)로 변경
    - 로그인 사용자와 질문한 사람일 때만 삭제 가능
    - 답변이 없는 경우에만 삭제 가능
    - 질문자와 답변 글의 모든 답변자가 같은 경우에만 질문자일 때 삭제 가능
    - 질문을 삭제할 때 답변 또한 삭제되야 하며, 실제 삭제가 아닌 deleted 상태 변경
    - 질문자와 답변자가 다르면 답변을 삭제할 수 없다.
    
    
- 객체 관계 매핑 팁
    - User와 Question 간의 관계를 매핑한다. User는 너무 많은 곳에 사용되기 때문에 User에서 관계를 매핑하기 보다는 Question에서 @ManyToOne 관계를 매핑하고 있다.
    - Question에 생성일을 추가한다.
    - JPA에서 LocalDateTime을 DB 데이터타입과 제대로 매핑하지 못하는 이슈 해결
        5-4 영상 참
    - 답변 기능을 담당할 Answer를 추가하고, Question, User와 매핑
    - Question에 Answer를 @OneToMany로 매핑.
        이와 같이 매핑함으로써 질문 상세보기 화면에서 답변 목록이 동작하는 과정 공유
    
    - QuestionController에서 보안 처리를 위해 구현한 중복 코드를 제거
    - 중복을 Exception을 활용한 제거와 Result와 같은 새로운 클래스르 추가해 제거