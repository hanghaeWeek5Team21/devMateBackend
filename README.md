# devMateBackend

팀원 : 고원구, 김건우 (frontend)   
김도형, 이태경 (backend)   

작업공간 : 게더타운, 노션

* ## 결과
    * [프로젝트 링크](http://www.devmate.org/)
    * [유튜브 링크](https://www.youtube.com/watch?v=iSRJttPh7XU)
      <br>

* ## Project
    * <details>
      <summary>아아디어</summary>
      <br>

      향해99 2기 동기들의 숫자는 대략 130명, 하지만 이중에 20명도 알지 못하고 있어, 서로를 더 알기위해 만든 미니프로젝트입니다.   
      개발자를 뜻하는 'Dev" + 동료를 뜻하는 "Mate"를 합쳐 프로젝트명 Dev-Mate로 정하였습니다.
      </details>
      <br>

    * <details>
      <summary>기능</summary>
      <br>
      
        * 회원가입/회원수정
        * 회원 이미지 S3 저장
        * 로그인/로그아웃   
          (SPRING SECURITY JSESSIONID)
        * 회원 조회  
        * 회원에게 좋아요/댓글 작성
        * 댓글 삭제

      </details>
      <br>

* ## Backend
    * <details>
      <summary>API 설계</summary>
        <br>
      
        /api
        * /user
            * method = POST
            * request = {username:"아이디",
              password:"비밀번호",
              name: "강호동",
              skill : "REACT",
              introduce : "안녕하세요!", image_url: "www.aws.s3.img.jpg"}
            * response = {res: true/false, msg: "회원가입이 성공하였습니다."}
            * 기능 = 회원가입
            
        * /user
          * method = GET
          * response = {res:true/false,
            msg: "회원이 조회되었습니다.",
            result: [ { id: user.id , name: user.name , skill: user.skill , introduce: user.introduce , image_url: user.image_url } , .... ]}
          * 기능 = 모든 회원을 조회합니다.

        * /user/{user_id}
          * method = GET
          * response = {res:true/false, msg: "단일 회원이 조회되었습니다.", result: {id: user.id , name: user.name , skill: user.skill , introduce: user.introduce , image_url: user.image_url, comments: [{author_id:author.id, content: comment.content, modified_at: localdatetime, created_at: localdatetime}, ... ] } }
          * 기능 = 단일 회원을 조회합니다.
            
        * /user?user_id={중복확인 아이디}
            * method = GET
            * request = ?user_id={중복확인 아이디}
            * response = {res=true/false, msg="아이디가 중복되었습니다."}
            * 기능 = 회원 가입 전 중복확인

        * /user/image
            * method = POST (multipartform / form)
            * request = {file : image}
            * response = {res=true/false, msg="이미지가 서버에 저장되었습니다.", image_url="www.aws.s3.img.jpg"}
            * 기능 = S3에 업로드된 이미지 저장, url 반환

        * /user/login
            * method = POST (form)
            * request = {username:아이디", password:"비밀번호"}
            * response = redirect "/"
            * 기능 = 스프링 부트 로그인
          
        * /comment?user_id={조회 유저}
          * method = GET
          * request = ?user_id={조회 유저}
          * response = {res: true/false, result:{author_id:comment.author_id, contents:comment.contents}
          * 기능 = 특정 유저의 모든 댓글 조회
          
        * /comment
          * method = POST
          * request = {contents: "댓글내용" ,
            user_id: "댓글이 달린사람의 id
            username: "댓글을 단 사람의 이름"}
          * response = {res: true/false, msg: "댓글이 작성되었습니다."}
          * 기능 = 댓글 작성

        * /comment/{comment_id}
          * method = DELETE
          * response = {res: true/false, msg: "삭제되었습니다."}
          * 기능 = 댓글 삭제

        * /comment
          * method = PATCH
          * request = {contents:"댓글내용"}
          * response = {res: true/false, msg: "수정되었습니다."}
          * 기능 = 댓글 수정

        * /likes
          * method = POST
          * request = {user_id: "좋아요가 달린사람 id"}
          * response = {res: true/false, msg: "좋아요가 작성되었습니다."}
          * 기능 = 좋아요 작성

        * /likes/{like_id}
          * method = DELETE
          * response = {res: true/false, msg: "좋아요가 삭제되었습니다."}
          * 기능 = 좋아요 삭제

    </details>
    <br>

    * <details>
      <summary>데이터베이스</summary>
        <br>

        * wanderer
            * user
                * email = str
                * nickname = str
                * password = binary
            * place
                * placeName = str
                * imageURL = str
                * location = str
                * likedUser = arr[email = str]
                * createdUser = str
    </details>
    <br>

    * <details>
      <summary>Mistakes were made</summary>  
      <br>
      
      </details>
      <br>
      
    * <details>
      <summary>CORS</summary>  
      <br>

      </details>
      <br>


* ## 협업
    * <details>
      <summary>git</summary>
      <br>
      
      백엔드는 브랜치를 아직 사용하지 않고 commit merge 만 조심하며 작성하였습니다.       
      원구님의 경우 front 에서 브랜치를 사용하셨습니다.
      </details>
      <br>

    * <details>
      <summary>notion</summary>
      <br>
      
      [Link](https://www.notion.so/21-3b3e8608d943459a93a9652418efd1b6)
      </details>

<br>

* ## 설치
    * <details>
      <summary>Route53, EC2, S3</summary>
      <br>
        
      </details>
