var main_chest = partition.mainPartition.mainChest;
var imageElement = document.getElementById('imageElement1');
var imagePath;

switch (main_chest) {
    case '벤치프레스':
        imagePath = '/routine_img/벤치프레스.png';
        break;
    case '인클라인벤치프레스':
        imagePath = '/routine_img/인클라인벤치프레스.png';
        break;
    case '체스트프레스머신':
        imagePath = '/routine_img/체스트프레스머신.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;

var main_back = partition.mainPartition.mainBack;
var imageElement = document.getElementById('imageElement2');
var imagePath;

switch (main_back) {
    case '랫풀다운':
        imagePath = '/routine_img/랫풀다운.png';
        break;
    case '시티드로우':
        imagePath = '/routine_img/시티드로우.png';
        break;
    case '풀업':
        imagePath = '/routine_img/풀업.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;

var main_shoulder = partition.mainPartition.mainShoulder;
var imageElement = document.getElementById('imageElement3');
var imagePath;

switch (main_shoulder) {
    case '사레레':
        imagePath = '/routine_img/사레레.png';
        break;
    case '숄더프레스머신':
        imagePath = '/routine_img/숄더프레스머신.png';
        break;
    case 'OHP':
        imagePath = '/routine_img/OHP.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;

var main_leg = partition.mainPartition.mainLeg;
var imageElement = document.getElementById('imageElement4');
var imagePath;

switch (main_leg) {
    case '데드리프트':
        imagePath = '/routine_img/데드리프트.png';
        break;
    case '레그익스텐션':
        imagePath = '/routine_img/레그익스텐션.png';
        break;
    case '레그컬':
        imagePath = '/routine_img/레그컬.png';
        break;
    case '레그프레스':
        imagePath = '/routine_img/레그프레스.png';
        break;
    case '스쿼트':
        imagePath = '/routine_img/스쿼트.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;

var main_aerobic = partition.mainPartition.mainAerobic;
var imageElement = document.getElementById('imageElement5');
var imagePath;

switch (main_aerobic) {
    case '트레이드밀':
        imagePath = '/routine_img/트레이드밀.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;


var sub_chest = partition.subPartition.subChest;
var imageElement = document.getElementById('imageElement6');
var imagePath;

switch (sub_chest) {
    case '벤치프레스':
        imagePath = '/routine_img/벤치프레스.png';
        break;
    case '인클라인벤치프레스':
        imagePath = '/routine_img/인클라인벤치프레스.png';
        break;
    case '체스트프레스머신':
        imagePath = '/routine_img/체스트프레스머신.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;


var sub_back = partition.subPartition.subBack;
var imageElement = document.getElementById('imageElement7');
var imagePath;

switch (sub_back) {
    case '랫풀다운':
        imagePath = '/routine_img/랫풀다운.png';
        break;
    case '시티드로우':
        imagePath = '/routine_img/시티드로우.png';
        break;
    case '풀업':
        imagePath = '/routine_img/풀업.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;


var sub_shoulder = partition.subPartition.subShoulder;
var imageElement = document.getElementById('imageElement8');
var imagePath;

switch (sub_shoulder) {
    case '사레레':
        imagePath = '/routine_img/사레레.png';
        break;
    case '숄더프레스머신':
        imagePath = '/routine_img/숄더프레스머신.png';
        break;
    case 'OHP':
        imagePath = '/routine_img/OHP.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;


var sub_leg = partition.subPartition.subLeg;
var imageElement = document.getElementById('imageElement9');
var imagePath;

switch (sub_leg) {
    case '데드리프트':
        imagePath = '/routine_img/데드리프트.png';
        break;
    case '레그익스텐션':
        imagePath = '/routine_img/레그익스텐션.png';
        break;
    case '레그컬':
        imagePath = '/routine_img/레그컬.png';
        break;
    case '레그프레스':
        imagePath = '/routine_img/레그프레스.png';
        break;
    case '스쿼트':
        imagePath = '/routine_img/스쿼트.png';
        break;
    
    default:
        // 텍스트 데이터에 맞는 이미지가 없을 경우 기본 이미지를 설정하거나 처리할 동작을 구현합니다.
        imagePath = 'default.jpg';
        break;
}

imageElement.src =imagePath;

