//package com.example.frontend.http;
//
//public class NetRetrofit {
//
//
//    private  static NetRetrofit outInstance = new NetRetrofit();
//    public static NetRetrofit getInstance(){ // 싱글톤으로 만들어줌.
//        return   outInstance;
//    }
//
//    public static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("자신이 만든 서버 ip 주소")
//
//            .client( new OkHttpClient.Builder()
//                    .hostnameVerifier(new NullHostNameVerifier())
//                    .sslSocketFactory(SSLUtil.getPinnedCertSslSocketFactory(context))
//                    .build()) // 이 부분은 글쓴이가 ssl 인증서를 통해 https 통신을 하기위해 추가한 부분 해당사항이 없다면 생략해도 된다
//
//            .addConverterFactory(GsonConverterFactory.create()) // gson 을 사용할경우
//            //만약 String으로 처리하고 싶다면addConverterFactory(ScalarsConverterFactory.create()) 을 사용
//            .build(); // 스프링 서버에 연결하고 레트로핏 초기화
//
//    public static RetrofitService service = retrofit.create(RetrofitService.class);
//
//
//    public RetrofitService getService(){// 싱글톤으로 만들어줌. 다른데서 이 함수를 호출하면 여기서
//        //만든 retfrofit service 를 사용할 수 있게 됌.
//        return service;
//    }
//}
