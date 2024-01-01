[second 7fd671b] feat: add Blocking content who not paid Site_user  
이때 아이디의 PAID 필드가 true여도 PAID 권한을 가지고 있지않다는 것을 발견  
해결 >  boolean hasPaidAuthority = authorities.stream()
.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_PAID"));

해야되는 작업
1. 질문 작성할 때 유료글 유무 설정 radio 버튼 만들기
2. 자기가 비멥서쉽일때 유료글을 작성한다면 비멤버쉽이어도 자신의 유료글은 볼 수 있어야한다.
3. 댓글도 마찬가지로 비멤버쉽은 유료글에 댓글도 작성 할 수 없어야한다.