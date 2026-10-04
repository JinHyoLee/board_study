# 📌 Spring MVC 기반 CRUD 게시판 프로젝트

본 프로젝트는 **Spring Framework(Legacy)**, **MyBatis**, **Oracle DB**, **JSP**를 활용하여 웹 개발의 가장 기본이자 핵심인 **CRUD(Create, Read, Update, Delete)** 기능을 완벽히 구현한 웹 애플리케이션입니다.

---

## 🏗️ 1. 전체 아키텍처 및 데이터 흐름 (Architecture & Flow)

스프링의 표준 패턴인 **Controller - Service - Mapper(DAO) - DB** 구조를 따르며, 사용자의 요청부터 DB 처리까지의 흐름은 다음과 같습니다.
1. **Presentation Layer (JSP / Controller)**: 사용자의 화면 요청(URL)을 받고 데이터를 수집하여 결과를 다시 화면에 전달합니다.
2. **Business Layer (Service)**: 핵심 비즈니스 로직을 처리하며, 요구사항에 맞게 영속성 계층을 호출합니다.
3. **Persistence Layer (Mapper / XML)**: MyBatis를 이용해 자바 객체와 SQL문을 매핑하고 실제 DB에 접근합니다.

---

## 📂 2. 주요 핵심 파일 구조 및 역할

### 1) Configuration & Settings (설정 파일)
* **`web.xml`**: 웹 애플리케이션의 시작점. `DispatcherServlet`을 등록하고, 브라우저에서 넘어오는 파라미터의 한글 깨짐을 방지하기 위해 `CharacterEncodingFilter`를 최상단에 배치했습니다.
* **`servlet-context.xml`**: 웹 관련 스프링 설정 파일. `views` 폴더 내 JSP 인식을 위한 `ViewResolver` 설정 및 컨트롤러를 자동으로 찾기 위한 `<context:component-scan base-package="com.blue.board" />`가 등록되어 있습니다.
* **`root-context.xml`**: DB 연동(DataSource, SqlSessionFactory, MyBatis 설정) 및 비즈니스 로직 관련 객체들을 관리하는 파일입니다. `typeAliasesPackage` 설정을 통해 `BoardVO` 클래스의 별칭을 부여했습니다.

### 2) Java Classes (자바 컴포넌트)
* **`BoardVO.java` (Domain)**: 게시글의 데이터(번호, 제목, 내용, 작성자, 날짜, 조회수)를 담는 그릇 객체입니다. DB 테이블 컬럼과 1:1 매칭됩니다.
* **`BoardController.java` (Controller)**: 사용자의 URL 요청(`GET`/`POST`)을 분기 처리하고, 화면(Model) 제어 및 리다이렉트(`redirect:`)를 담당합니다.
* **`BoardService.java` & `BoardServiceImpl.java` (Service)**: 컨트롤러와 매퍼 사이의 징검다리이자 비즈니스 로직을 수행하는 계층입니다.
* **`BoardMapper.java` (Mapper Interface)**: SQL을 호출하기 위한 자바 인터페이스로, MyBatis XML 파일의 각 쿼리 `id`와 메서드명이 연결됩니다.

### 3) MyBatis & Views (쿼리 및 화면)
* **`boardMapper.xml`**: 실제 Oracle DB에 날릴 SQL문(`SELECT`, `INSERT`, `UPDATE`, `DELETE`)들이 저장된 파일입니다.
* **JSP Files (`list.jsp`, `register.jsp`, `get.jsp`, `modify.jsp`)**: JSTL과 EL 태그(`${board.title}`)를 사용하여 서버로부터 받은 데이터를 브라우저에 동적으로 그려주는 화면입니다.

---

## 🔄 3. 기능별 차근차근 동작 방식 (Step-by-Step Flow)

### 🟢 1. R (Read) - 게시글 목록 조회 (`/board/list`)
1. 사용자가 브라우저에 `http://localhost:8080/board/list`를 요청합니다.
2. `BoardController`의 `list()` 메서드가 실행됩니다.
3. 컨트롤러는 `BoardService.getList()` ➡️ `BoardMapper.getList()`를 거쳐 `boardMapper.xml`의 `id="getList"`(`<select>` 쿼리)를 실행합니다.
4. Oracle DB에서 조회된 결과 레코드들이 `List<BoardVO>` 형태로 자동 변환되어 상위 계층으로 반환됩니다.
5. 컨트롤러는 `model.addAttribute("list", ...)`를 통해 데이터를 담고 `board/list.jsp`로 포워딩합니다.
6. `list.jsp`에서 JSTL의 `<c:forEach>` 태그가 작동하여 데이터를 표(Table) 형태로 화면에 출력합니다.

### 🔵 2. C (Create) - 게시글 등록 (`/board/register`)
* **화면 요청 (GET)**: `/board/register`로 접속하면 컨트롤러가 단순히 `register.jsp` 화면만 띄워줍니다.
* **실제 처리 (POST)**:
  1. 사용자가 화면 폼(Form)에 제목, 내용, 작성자를 적고 `[등록하기]`를 누르면 데이터가 `POST` 방식으로 전송됩니다.
  2. 스프링의 데이터 수집 기능에 의해 폼의 `name` 속성과 일치하는 `BoardVO` 객체에 값이 자동으로 채워집니다.
  3. `BoardController` ➡️ `Service` ➡️ `Mapper`를 거쳐 `boardMapper.xml`의 `id="insert"`(`<insert>` 쿼리)가 실행되어 DB에 저장됩니다. (글 번호는 `SEQ_BOARD.NEXTVAL` 시퀀스로 자동 생성)
  4. 등록 처리가 끝나면 컨트롤러는 `return "redirect:/board/list";`를 실행하여 사용자를 목록 화면으로 강제 이동시킵니다.

### 🟡 3. R & U (Read & Update) - 상세보기 및 수정 (`/board/get`, `/board/modify`)
* **상세보기 / 수정 화면 (GET)**:
  1. 목록에서 제목(`<a>` 태그)을 누르면 글 번호를 주소창에 달고 `/board/get?bno=7` 형식으로 요청합니다.
  2. 컨트롤러는 `@RequestParam("bno")`로 글 번호를 받아서 `service.get(bno)`를 통해 DB에서 단 1건의 게시글 데이터(`BoardVO`)를 가져옵니다.
  3. 가져온 데이터를 `board`라는 이름으로 모델에 담아 `get.jsp`(상세화면) 또는 `modify.jsp`(수정화면)로 보냅니다.
* **실제 수정 처리 (POST)**:
  1. `modify.jsp`에서 사용자가 내용을 고치고 `[수정완료]`를 누르면 데이터가 `POST`로 날아갑니다.
  2. 컨트롤러는 `BoardVO`를 수집하여 `service.modify(board)`를 호출하고, 내부적으로 `UPDATE board SET ... WHERE bno = #{bno}` 쿼리가 돌아가며 DB가 갱신됩니다.
  3. 수정이 완료되면 다시 목록(`/board/list`)으로 리다이렉트됩니다.

### 🔴 4. D (Delete) - 게시글 삭제 (`/board/remove`)
1. 사용자가 수정 화면(`modify.jsp`)에서 `[삭제하기]` 버튼을 누르면 자바스크립트(`this.form.action='/board/remove'`)에 의해 폼의 목적지가 변경되며 `POST` 전송됩니다.
2. 컨트롤러의 `remove(@RequestParam("bno") Long bno)` 메서드가 호출됩니다.
3. `service.remove(bno)`를 거쳐 `boardMapper.xml`의 `DELETE FROM board WHERE bno = #{bno}` 쿼리가 수행되어 해당 번호의 글이 DB에서 영구 삭제됩니다.
4. 삭제 완료 후 목록(`/board/list`)으로 리다이렉트되어 삭제된 상태의 목록을 보여줍니다.

---

## 🛠️ 4. 기술 스택 및 환경 (Tech Stack)
* **Language**: Java 11 / HTML5 / CSS3
* **Framework**: Spring Framework 5.x (MVC)
* **ORM / Database Link**: MyBatis 3.x / MyBatis-Spring
* **Database**: Oracle Database 11g / 19c
* **Server**: Apache Tomcat 9.0
* **IDE**: Eclipse (STS)

---

## 💡 5. 트러블 슈팅 경험 (Troubleshooting)

* **`BeanCreationException` 해결**: 스프링 컨테이너가 컴포넌트 스캔 시 각 계층의 의존성 주입(DI)을 처리하는 과정에서 패키지 누락 및 의존성 주입 규칙을 확인하고 해결했습니다.
* **`ClassNotFoundException: BoardVO` 해결**: MyBatis 매퍼 XML 파일에서 별칭(Type Alias)을 찾지 못하는 문제를 `root-context.xml` 내 `typeAliasesPackage` 설정을 추가함으로써 해결했습니다.
* **`cvc-id.3` XML Schema 검증 에러**: `web.xml` 내부 태그 정렬 규칙으로 인해 발생한 이클립스 Validation 에러를 표준 배치 순서(`<filter>` ➡️ `<listener>` ➡️ `<servlet>`)에 맞게 정렬하여 해결했습니다.
* **404 Page Not Found 에러**: 톰캣 서버의 Context Path(`path="/board"`) 설정과 스프링 RequestMapping 주소가 겹쳐 주소가 왜곡되던 문제를 톰캣의 Path 설정을 루트(`/`)로 변경하여 해결했습니다.
