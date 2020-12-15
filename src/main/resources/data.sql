insert into tax_income_summary (tax_income_summary_id, carried_amount, personal_donation, corporate_donation, ngo_donation, item_donation,
etc_donation, support_fund, grant_fund, other_fund, etc_business_profit, interest_profit, conversion_profit, exchange_profit, etc_non_business_profit)
values (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

insert into tax_outcome_summary (tax_outcome_summary_id , labor_cost, consumable_cost, rent_cost, etc_operation_cost, remedy_cost, event_cost, promotion_cost,
etc_business_cost, vat_cost, tax_processing_cost, office_cost, etc_recruit_cost, gas_cost, communication_cost, etc_cost,
conversion_cost, exchange_cost, etc_non_business_cost)
values (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

INSERT INTO ORGANIZATION (organization_id, name, password, email, phone_number, register_number, account, TAX_INCOME_SUMMARY_ID, TAX_OUTCOME_SUMMARY_ID)
VALUES (1, 'test', 'dGVzdHB3', 'testmail', 'testphone', '12345678', 'nick', 1, 1);

insert into activity (ACTIVITY_ID, LIMITATION, NAME, PERFORMANCE, ORGANIZATION_ID)
values (1, 'woo', 'name', 'wow', 1);

insert into campaign (CAMPAIGN_ID, END, NAME, START, ORGANIZATION_ID)
values (1, '03-01', 'name', '02-01', 1);

insert into campaign_cost (CAMPAIGN_COST_ID, CONTENT, COST, USE_DATE, ORGANIZATION_ID)
values (1, 'content', 2000, 'date', 1);

insert into activity (ACTIVITY_ID, LIMITATION, NAME, PERFORMANCE, ORGANIZATION_ID)
values (2, 'woo', 'name', 'wow', 1);

insert into campaign (CAMPAIGN_ID, END, NAME, START, ORGANIZATION_ID)
values (2, '03-01', 'name', '02-01', 1);

insert into campaign_cost (CAMPAIGN_COST_ID, CONTENT, COST, USE_DATE, ORGANIZATION_ID)
values (2, 'content', 2000, 'date', 1);
--insert into ORGANIZATION values(1, '123', null, 'email.com', 'name', '321321321','0101010101', '123123123', null, null, null)

-- Insert to Category Table
insert into category (CATEGORY_ID, NAME)
values (1, 'emergency');
insert into category (CATEGORY_ID, NAME)
values (2, 'disaster');
insert into category (CATEGORY_ID, NAME)
values (3, 'children'); -- 어린이
insert into category (CATEGORY_ID, NAME)
values (4, 'senior'); -- 어르신
insert into category (CATEGORY_ID, NAME)
values (5, 'disabled');
insert into category (CATEGORY_ID, NAME)
values (6, 'local'); -- 지역
insert into category (CATEGORY_ID, NAME)
values (7, 'multiculture'); -- 지구촌
insert into category (CATEGORY_ID, NAME)
values (8, 'education');
insert into category (CATEGORY_ID, NAME)
values (9, 'health'); -- 건강
insert into category (CATEGORY_ID, NAME)
values (10, 'art');
insert into category (CATEGORY_ID, NAME)
values (11, 'environment');
insert into category (CATEGORY_ID, NAME)
values (12, 'animal');
insert into category (CATEGORY_ID, NAME)
values (13, 'etc');

insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (1, '사단법인해피칠드런', 'reg1', '해피칠드런은 경기도 외교통상부 산하 국제NGO 단체입니다.', null, '차별을 겪는 빈민',
 '심리 상담을 통한 아동의 심리적 안정을 도모하여 적응력을 높임, 안정적인 가정생활과 사회적응이 가능하도록 도움',
 '본 법인은 해외 개발도상국 또는 분쟁국에 대한 노인, 아동, 여성 등 인종, 종교, 문화등의 차별을 배제하고 빈민들에 대한 구호 및 교육, 급식지원, 시설 운영, 의료 등의 지원을 통해 빈민들의 생명과 인간 존엄성을 회복하기 위한 전반적인 활동을 수행하며 또한 국내외적으로 문화적 이질감 해소를 위한 노력의 일원으로 세계문화교육과 참여형 기부캠페인 진행을 통한 글로벌 시민의식을 형성하고자 합니다.',
 'plan');

insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (2, '사단법인 따뜻한하루', 'reg2', '달콤하고 따스한 사람 사는 이야기가 담긴 꽃한송이', null, '경제적 취약계층',
 '필리핀 빈민촌 아동 생계 및 기초 생활 지원, 1인 거주 이웃들에게 사랑의 연탄 배달',
 '해외 10개국 아동 센터에서 방과 후 수업, 무료급식을 운영하며, 국내에는 긴급 치료비 지원 및 결손 가정을 후원하고 있습니다. 1대2 결연을 통해서는 아동들이 교육을 받을 수 있도록 지원하고 있습니다.',
 'plan');

insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (3, '재단법인 녹색하루', 'reg3', '시민과 함께 더 나은 녹색사회를 만들기 위해 환경을 보전하고 미래를 준비하는 환경 NGO', null, '환경파괴 피해 동물',
 '고속도로 위에서 방황하는 맹꽁이를 구출하여 다시 자연으로 보내줌, 유튜브 홍보 영상 제작을 통해 심각성을 널리 알림',
 '녹색하루는 물 및 자원순환, 기후와 에너지, 미세먼지 및 화학물질, 생태 등을 미래세대의 관점으로 바라보며 인류와 더불어 사는 지속가능한 생태계 유지를 위해 활동합니다.',
 'plan');

insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (4, 'A지역아동센터', 'reg4', '아이들의 꿈을 실현하는데 비전을 두고 있습니다.', null, '저소득층 아동 청소년',
 '저소득층 학생들의 공부환경 조성 및 여건 구축',
 'A지역아동센터는 2000년 7월에 공부방으로 개설하여 2005년에 아동복지법에 준하여 지역아동센터로 정식 등록하여 지역사회 아동의 보호, 교육, 건전한 놀이와 오락을 제공합니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (5, '메디피스', 'reg5', '모두가 평등하게 건강할 권리를 누리는 세상을 만듭니다.', null, '코로나19 의료진',
 '코로나19 방역 지원 국내 의료진 심리 케어 지원 프로그램 활성화',
 '메디피스는 가난으로 발생하는 질병을 예방하기 위한 활동과 긴급구호 활동을 하는 의료전문 글로벌 민간구호단체 입니다. 메디피스의 의료지원 활동은 특정 종교나 특정 이데올로기에 구속되지 않으며, 출신 민족이나 인종에 구애받지 않습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (6, '사랑장애인복지관', 'reg6', '장애인들의 생애주기별 특성에 따른 다양한 환경을 제공하여 장애인 스스로가 자립할 수 있는 기반을 만들기 위해 노력합니다.', null, '취약계층 장애인',
 '투병 생활 중인 어르신과 아드님의 여행 사업, 심리상담을 통한 심리적 안정을 도모',
 '사랑장애인복지관은 장애인들의 생이주기별 특성에 따른 다양한 환경을 제공하여 장애인 스스로가 자립할 수 있는 기반을 만들고자 합니다. 우리는 모든 장애인들과 함께 합니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (7, '사단법인링커', 'reg7', '사람과 사랑을 연결합니다.', null, '청소년 미혼모',
 '청소년 미혼모들에게 자립할 수 있도록 지속적인 교육지원',
 '외교부 산하 사단법인 NGO-LINKER는 국내 사업으로 청소년미혼모를 경제적, 정서적으로 지원하여 자립을 돕고 있고 해외 사업으로는 개발도상국을 지원하여 빈곤퇴치와 지역개발을 돕고 있습니다. 특히 자립에 성공한 대상자들을 해외 사업에 적극 투입함으로써, 받았던 사랑을 잊지 않고 다시 흘려 보내는 아름다운 선순환을 꿈꾸고 있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (8, '기쁨종합사회복지관', 'reg8', '희망을 나누는 좋은 이웃 기쁨종합사회복지관입니다.', null, '취약계층 어르신',
 '코로나19로 밖을 나갈 수 없는 어르신들을 위한 반찬 기부',
 '기쁨종합사회복지관은 서비스지원팀, 지역사회조직팀, 사례지원팀, 외국인노동자센터, 기획운영팀 등 5개 팀이 하나가 되어 지역사회의 복지증진과 지역주민의 행복을 위해 노력하고 있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (9, '사단법인 한국다문화청소년협회', 'reg9', '다문화청소년들의 행복한 삶을 영위하기 위한 단체입니다.', null, '가정 폭력 피해 다문화가정 아동',
 '가정폭력으로 인해 피해받은 다문화 가정 아동 청소년 교육 지원 사업, 심리 상담을 통한 아동의 심리적 안정을 도모',
 '대한민국은 글로벌 시대를 맞이하여 국제결혼 및 국제노동인구가 급속히 증가함에 따라 다문화사회로 변화되고 있습니다. 다문화청소년들이 건전한 성장과 발달을 도모함으로서 사회구성원으로서의 역할을 다하고 민주시민으로 성장하는 것을 돕고자 합니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (10, '행복한 유기견세상', 'reg10', '동물이 행복한 세상을 만듭니다.', null, '안락사 위기 유기동물',
 '유기동물 건강검진 및 예방접종',
 '행복한 유기견세상은 2007년 2월에 설립된 단체로서 보호소를 설립하여 안락사위기의 유기견을 구조하여 보호, 재 입양활동을 하여왔습니다. 2008년 비영리민간단체 인가를 받았으며, 현재 30000여명의 회원이 등록되어있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (11, '함께하는 한숲', 'reg11', '아이들과 함께하는 한숲', null, '코로나19로 갈 곳을 잃은 아이들',
 '부모 맞벌이 초등학생 놀이 및 교육 지원',
 '비영리사단법인 함께하는 한숲 해피로그입니다. 2004년 개설하여 현재 지역사회 아동의 보호, 교육, 건전한 놀이와 오락을 제공합니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (12, '사단법인 여성인권 동감', 'reg12', '이혼, 사별, 비혼모, 조손가족들을 지원 의료, 법률, 교육, 구직, 자녀 지원 ', null, '사회적 약자 복지',
 '사회적 약자 의료, 법률, 교육, 구직, 자녀 지원',
 '본 법인은 2012년 05월 창립하여 현재까지 회비 및 비정기기부금, 기업기부금 등으로 운영되어지고 있습니다. 비영리법인으로써 지역사회 내에 정부지원 배제 및 복지관 이용 등을 하지 않고 있는 한부모 이혼, 사별, 비혼모, 조손가족들을 오프라인 아웃리치 및 온라인 홈페이지, 트위터, 인스타그램, 트위터, 블로그 등 활동을 통해 직접 발굴하여 개별맞춤 지원 의료, 법률, 교육, 구직, 자녀 지원 등의 활동이 이루어지고 있습니다',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (13, '(사)유니세프한국위원회', 'reg13', '유니세프는 190여 개의 국가 및 영토에서 모든 어린이들, 특히 가장 열악한 어린이들의 권리와 복지를 증진하기 위해 일합니다.', null, '어린이 권리와 복지 증진',
 '어린이 생계 및 기초생활지원',
 '유니세프는 190여 개의 국가 및 영토에서 모든 어린이들, 특히 가장 열악한 어린이들의 권리와 복지를 증진하기 위해 일합니다. 유니세프 11,500명 직원의 87%는 155개 국가와 영토에 있는 현지 사무소에서 어린이들을 위해 직접 활동하고 있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (14, '서울환경운동연합', 'reg14', '옥상과 한 뼘 빈터마다 텃밭을 일구고, 탄소연료 대신 태양으로 에너지 자립을 실현하는 도시에서 자전거를 탄 시민들이 금모래 은물결 부서지는 한강으로 강수욕을 떠나는 서울의 미래를 꿈꿉니다.', null, '환경문제 인식 및 정책 개선',
 '환경문제 인식 및 정책 개선',
 '서울환경연합은 초기 환경운동연합 전국 사무처의 역할을 해왔으며, 서울지역의 생태환경적 요구를 해결하는 지역자치 시민환경운동에 힘써 왔습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (15, '초록우산 어린이재단', 'reg15', '국내외 아동복지사업, 애드보커시, 모금사업, 연구조사 등을 폭넓게 실시하고 있는 아동옹호대표기관입니다.', null, '아동•청소년 생계 및 기초생활 지원',
 '아동•청소년 생계 및 기초생활 지원',
 '1948년 문을 연 초록우산 어린이재단은 1950년 6.25전쟁 고아 구호사업에 집중한 이후 현재에 이르기까지 국내외 아동복지사업, 애드보커시, 모금사업, 연구조사 등을 폭넓게 실시하고 있는 아동옹호대표기관입니다. 현재 국내 외 아동 연간 약 100만명에게 직간접 도움을 주며 그들의 미래를 열어가는 일을 하고 있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (16, '글로벌케어', 'reg16', '글로벌케어는 국내외 인도적지원 및 의료환경개선과 보건사업지원 등의 국제개발에 앞장서는 비영리단체입니다.', null, ' 아프리카, 중남미의 최빈국',
 '아프리카, 중남미의 최빈국 의료 지원',
 '글로벌케어는 한국에서 시작되어 국제사회로 나아가는 자생적 국제개발, 보건의료 NGO로서 지구촌 곳곳의 소외된 이웃들이 희망을 꿈꿀 수 있도록 국내외 인도적지원 및 의료환경개선과 보건사업지원 등의 국제개발에 앞장서는 비영리단체입니다. 글로벌케어는 보건 의료 전문 기관으로 동남아시아, 아프리카, 중남미의 최빈국에서 활동하고 있으며, 각종 자연 재해나 인적 재난으로부터 고통을 받는 이웃들에게 사랑을 실천하고 있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (17, '사단법인 동물보호단체 행강', 'reg17', '피학대동물 및 유기동물 구조 보호및 동물복지 정책을 개선, 사설 유기견보호소 운영.', null, '유기동물 구조',
 '유기동물 구조 및 지원',
 '사단법인 동물보호단체 행강은 200여 유기견들을 보호하는 행강 사설유기견보호소를 운영하고 있으며 대한민국의 동물보호 및 동물복지 정책 발전을 위해 최선을 다하고 유기동물의 보호와 복지에 대한 최선의 봉사를 수행함으로써 동물과 사람이 행복하게 공존하는 사회를 이루는데 기여하고자 함을 목적으로 하고 대한민국에서 동물의 임의도살 금지법 제정을 시작으로 이땅의 모든 동물들이 가져야할 행복의 권리를 찾아주기 위해 최선을 다하고 있습니다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (18, '천연기념물동물유전자원은행', 'reg18', '천연기념물 동물 및 한국야생동물의 유전자원 보존, 연구 사업을 수행하는 단체', null, '천연기념물 동물의 유전자원 보존, 연구 사업 수행',
 '천연기념물 동물의 유전자원 보존, 연구 사업 수행',
 '천연기념물 동물의 유전자원 보존, 연구 사업을 수행하며, 유전자원 확보에 필수적인 천연기념물 동물의 구조, 치료, 재활, 관리, 질병연구 활동을 목적으로 설립된 대한민국 문화체육관광부 소관의 사단법인이다.',
 'plan');

 insert into donation (DONATION_ID, NAME, REGISTRANT, SHORT_INTRODUCTION, PROFILE_URL, TARGET, CONTENT, INTRODUCTION, PLAN)
values (19, '하당노인복지관', 'reg19', '최고의 가치와 감동, 호텔그이상의 서비스로 어르신을 모시겠습니다.', null, '어르신, 장애인, 의료 지원',
 '취약계층 어르신, 장애인 생계 및 기초생활 지원',
 '하당노인복지관은 이랜드복지재단이 목포시로부터 위탁받아 운영하는 노인복지전문기관으로 어르신들의 건강하고 행복한 노후생활을 돕기 위해 다양하고 전문적인 복지서비스를 제공하고 있습니다.',
 'plan');

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (1, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (1, 4);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (1, 7);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (1, 9);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (2, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (2, 4);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (2, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (2, 9);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (3, 9);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (3, 11);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (3, 12);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (4, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (4, 8);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (5, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (5, 9);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (6, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (6, 4);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (6, 5);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (6, 9);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (7, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (7, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (7, 8);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (8, 4);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (8, 6);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (9, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (9, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (9, 7);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (9, 8);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (10, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (10, 9);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (10, 12);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (11, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (11, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (11, 8);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (12, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (12, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (12, 8);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (13, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (13, 7);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (13, 9);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (14, 6);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (14, 11);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (15, 3);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (15, 6);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (16, 9);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (16, 7);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (16, 8);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (17, 12);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (17, 11);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (18, 12);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (18, 11);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (18, 9);

insert into donation_category (DONATION_ID, CATEGORY_ID)
values (19, 4);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (19, 5);
insert into donation_category (DONATION_ID, CATEGORY_ID)
values (19, 9);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (1, 2010000, null, '아동개별상담', 8, 1);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (2, 1203000, null, '단체홍보활동', null, 1);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (3, 823900, '5회', '아동집단상담', 20, 1);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (4, 5000000, '8회', '무료급식', 200, 2);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (5, 1580000, '7회', '음료과자비', 200, 2);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (6, 8592800, '1000장', '연탄전달', 130, 2);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (7, 2000000, '유튜브공식계정업로드', '홍보영상촬영', null, 3);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (8, 2059730, null, '맹꽁이 구조비', null, 3);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (9, 4269920, null, '인건비 및 식비', 120, 3);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (10, 2500000, '중간고사대비', '수학강사비', 30, 4);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (11, 1500000, null, '국어강사비', 30, 4);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (12, 3700000, null, '영어강사비', 30, 4);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (13, 2510200, null, '스포츠 레슨', 15, 4);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (14, 3600000, '2회', '단체워크숍강사비', 120, 5);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (15, 450000, '2회', '워크숍 교구비', 15, 5);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (16, 3600000, '2회', '개인상담지원비', 15, 5);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (17, 1650000, null, '워크숍 및 상담 다과비', 165, 5);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (18, 800000, '7박', '숙박비', 5, 6);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (19, 1500000, '15회', '심리상담치료', 2, 6);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (20, 2100400, '3개월분', '유산균구입', 10, 6);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (21, 5221000, null, '대안학교 수업지원', 20, 7);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (22, 3012000, null, '대안학교 운영비', 20, 7);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (23, 3900000, null, '선생님 인건비', 20, 7);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (24, 1283270, null, '밑반찬 조리용 삭자재 구입', 100, 8);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (25, 701200, null, '떡 구입비', 100, 8);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (26, 4208120, null, '어르신 무료급식소 운영비', 210, 8);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (27, 3920000, null, '심리치료비', 10, 9);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (28, 3000000, null, '교복지원비', 10, 9);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (29, 2151000, null, '신학기 가방지원', 10, 9);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (30, 4000000, '40두', '예방접종/구충제', 20, 10);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (31, 2002500, null, '홍역/사상충/피부염 치료', 20, 10);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (32, 7000000, '9두', '혈검/X-Ray 등 검사', 20, 10);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (33, 3000000, null, '저학년학습지', 100, 11);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (34, 4000000, null, '고학년학습지', 100, 11);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (35, 2000000, null, '인터넷강의', 20, 11);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (36, 1900000, null, '외국인 노동자 미혼모여성 의료지원', 5, 12);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (37, 200000, '32가정', '조손가정 자녀 학습교재 및 학용품세트 지원', 45, 12);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (38, 1900000, null, '겨울난방매트지원', 4, 12);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (39, 5000000, null, '코로나19로 예방접종을 받지 못한 콩고 어린이 지원', 5000, 13);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (40, 9899800, null, '아프리카 사이클론 피해어린이 지원', 5, 13);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (41, 4500000, null, '책가방 지원', 16, 13);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (42, 2050900, null, '구강수분보충염 전달', 18990, 13);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (43, 4800000, null, '교재구입', 20, 14);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (44, 3000000, '200부', '캘린더 제작', 200, 14);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (45, 2200000, '2개', '기후음원영상콘텐츠제작', 2, 14);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (46, 15000000, null, '아동 가정 생계지원', 4, 15);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (47, 5000000, null, '아동 가정 보육지원', 5, 15);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (48, 15000000, null, '의료비(조모 치료비)지원', 10, 15);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (49, 33000000, null, '홍수지원', 600, 16);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (50, 8800000, null, '코로나19로 문을 닫을 위기에 놓인 병원 지원', 800, 16);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (51, 21000000, null, '2020 레바논 대폭발 참사 긴급구호', 2000, 16);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (52, 10000000, null, '안락사 구조 지원', 200, 17);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (53, 20000000, null, '모낭충 치료', 300, 17);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (54, 1000000, null, '유기견 보호소 지원', 400, 17);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (55, 2000000, null, '호랑이, 표범 서식지 밀렵예방 및 감시활동 지원', 4, 18);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (56, 2000000, null, '아시아 큰고양이과동물 보전 프로젝트', 10, 18);

insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (57, 7000000, null, '도예 및 공예 제작 지원', 12, 19);
insert into donation_cost_preview (ID, COST, ETC, NAME, TARGET_NUM, DONATION_ID)
values (58, 5900000, null, '어르신들의 배움 지원', 50, 19);

-- User
insert into USER (USER_ID, CREATED_DATE, MODIFIED_DATE, EMAIL, NAME, ROLE)
values (1, '2020-12-03T01:11:49.981', '2020-12-03T01:11:49.981', 'sphong0417@gmail.com', 'sphong', 'GUEST')