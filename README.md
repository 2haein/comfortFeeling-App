# comfort_feeling (감정을 위로해)

중앙대학교 캡스톤 프로젝트

### 1.프로젝트 개요
자신의 감정을 기록하고 자신의 위치를 이용해 주변 지역 사람들과 감정을 공유하여 서로 공감하고 위로받을 수 있는 앱 기반의 플랫폼

### 2.추진 배경 동기
장기간에 걸친 코로나 블루의 심각성때문에 남녀 불문하고 모든 나이대의 사람들이 사회적 고립감에 빠져있습니다. 건강보험심사평가원에서는 20대 청년들의 공황장애, 우울증 증가폭이 다른 연령대와 비교해서 두드러진다고 발표했습니다. 저희는 이것이 코로나19, 취업난과 더불어 SNS의 발달로 또래끼리 자신의 상황을 비교하면서 자존감이 낮아졌기 때문이라고 생각했습니다.. 이런 스트레스로 마음이 다치는 악순환을 좀 완화시켜보자는 생각을 하게 되었습니다. 

 기존에도 감정일기 앱이 존재하지만 아직 위치기반을 통해 주변 사람들에게 내 감정을 공유하고 위로받는 서비스는 없습니다. 그래서 주변 사람들의 감정을 서로서로 보살펴줄 수 있는 기능을 수행할 수 있는 프로젝트를 계획하게 되었습니다.

### 3.요구분석
#### 요구분석
- 기존의 일기장은 단순히 자기 감정의 기록 저장 목적이 강하고 타인과의 공유기능은 부재
- 타인과의 감정교류 및 공감을 통해서 치유받을 수 있는 서비스의 부재
- 위치기반의 감정교류 서비스는 아직 없음.
- 기존 감정일기에는 아직 얼굴표정을 기반으로 해서 내 감정을 분석해주는 기능은 존재하지 않음. 스스로 자신의 감정을 잘 모르겠을 때, 표정을 통해 감정을 분석하고 그에 따라서 자동으로 감정의 수치를 정해줄 필요가 있음.




#### 필요성
- 여러 요인들로 인해 자존감이 떨어지고, 우울한 감정을 느끼는 사람들이 주변에 사는 타인의 공감을 통해서 자존감 회복에 도움이 될 수 있다.
- 멀리 사는 사람보다 내 주변의 누군가가 나의 감정에 공감해줄 때 효과가 더 크다고 생각해서 위치기반을 통한 것이 필요하다고 생각했다.
- 기존 SNS에는 자신의 좋은 점만을 올리고 익명성이 보장이 되지 않아서 감정적으로 힘든 사람들이 상대적 박탈감을 얻을 수 있는데, 이 서비스는 익명성이 보장되어 좀 더 편하게 자신의 감정을 표출할 수 있다. 이에 자신의 감정에 솔직해질 수 있고, 감정변화를 통해 내 삶을 점검하면서 자존감 회복에 도움이 될 수 있다.

### 4.팀원 및 역할
|이름|역할|
|---|---|
|이해인|감정 그래프 기능 개발, 회원관리 기능, 회원 Auth Api 기능 개발|
|한승남|감정 기록 Api, 나의 History 내역 화면, history Api|
|전현욱|Navigation 목록 표출, 메인 지도 화면|

### 5.프로젝트 수행 방안
#### 5.1 주요 기술 스택
<img src="https://source.android.com/setup/images/Android_symbol_green_RGB.png" width="200"/> <img src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg" width="200"/> <img src="https://webimages.mongodb.com/_com_assets/cms/MongoDB_Logo_FullColorBlack_RGB-4td3yuxzjs.png?auto=format%2Ccompress" width="200"/> <img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="200"/> 

- Front 프레임워크 (Android)
- Server 프레임워크 (Spring Boot)
- Database (MongoDB)
- 백엔드 서버 (개발 초기 Spring Boot 내장 톰캣)
- 분산 버전 관리 툴 (Github)

#### 5.2 주요 기능
- 위치기반 공유 감정 기록장
- 감정 변화 그래프
- 카메라를 통한 얼굴 감정 인식

#### 5.3 예시 화면

<img src="https://github.com/codeBoogie/comfort_feeling/blob/develop/extra_folder/profile.jpg" width="300"/>&nbsp;&nbsp;<img src="https://github.com/codeBoogie/comfort_feeling/blob/develop/extra_folder/firstScreen.jpg" width="300"/>
<img src="https://github.com/codeBoogie/comfort_feeling/blob/develop/extra_folder/list.jpg" width="300"/>&nbsp;&nbsp;<img src="https://github.com/codeBoogie/comfort_feeling/blob/develop/extra_folder/graph.jpg" width="300"/>
<img src="https://github.com/codeBoogie/comfort_feeling/blob/develop/extra_folder/read.jpg" width="300"/>&nbsp;&nbsp;<img src="https://github.com/codeBoogie/comfort_feeling/blob/develop/extra_folder/test.jpg" width="300"/>
