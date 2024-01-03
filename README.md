[second 7fd671b] feat: add Blocking content who not paid Site_user  
이때 아이디의 PAID 필드가 true여도 PAID 권한을 가지고 있지않다는 것을 발견  
해결 >  boolean hasPaidAuthority = authorities.stream()
.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_PAID"));

해야되는 작업
1. 질문 작성할 때 유료글 유무 설정 radio 버튼 만들기
2. 자기가 비멥서쉽일때 유료글을 작성한다면 비멤버쉽이어도 자신의 유료글은 볼 수 있어야한다.
3. 댓글도 마찬가지로 비멤버쉽은 유료글에 댓글도 작성 할 수 없어야한다.


## 3번 선택 미션 사항 중 오류 발생
> 3번 선택미션을 했을때 오류 발생
> * NotProd에 만든 로그인 정보들로 로그인이 안됨
> 
> ```userService.create("user"+i, "user"+i+"@email.com", passwordEncoder.encode("password"+i), true);```
> 
> 이미 userService의 create메서드에서 알아서 암호화 처리를 해주는데 위 코드에도 작성하므로써 비밀번호를 두번 암호화하는 꼴이 됨
> 
> ```userService.create("user"+i, "user"+i+"@email.com", "password"+i , true);```
> 
> 이렇게 바꾸었더니 원하는 대로 로그인 됨