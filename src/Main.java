import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        //파일을 읽기 위해 버퍼 리더 사용
        BufferedReader reader = new BufferedReader(
                new FileReader("test.umm")
        );
        //출력 파일을 생성한다.
        File file = new File("test.c");

        //한 줄 씩 읽어오기위해 문자열 선언
        String str;

        //파일에 쓰기위해 bufferedwriter
        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);

        // "엄"이 첫 등장인지 아닌 지 확인
        //변수는 총 세개 가능하므로 각각의 변수가 첫 등장인지 등장했었는지 확인한다.
        boolean umm_flag = false;
        boolean uumm_flag = false;
        boolean uuumm_flag = false;



        while ((str = reader.readLine()) != null) {
            //식? 일 경우 flag
            boolean sik_flag = false;
            //"어떻게" 가 들어왔을 경우 c파일 프로그램의 시작 부분
            if (str.equals("어떻게")) {
                writer.write("#include <stdio.h>\nint main() {\n");
            }
            //"이 사람이름이냨ㅋ" 일 때 c 파일의 끝 부분
            if (str.equals("이 사람이름이냐ㅋㅋ")) {
                writer.write("return 0;\n}");
            }

            int ucnt = 0;
            boolean daeib = false;
            int u_cont = 0;
            StringBuilder modi = new StringBuilder();

            int num = 0;
            //정수형 출력 시
            if (str.contains("식") && str.contains("!")) {
                for (int i = 0; i < str.length(); i++) {
                    //어 등장 시 몇번째 변수 확인 위해 u_cont 를 증가
                    if (str.charAt(i) == '어') {
                        u_cont++;
                    }
                    //.이 등장 시 +1
                    if (str.charAt(i) == '.') {
                        modi.append("+1");
                    }
                    //, 일 때 -1
                    if (str.charAt(i) == ',') {
                        modi.append("-1");
                    }
                    //공백일때는 곱셈 처리 주의할 점은 앞에 계산 완료 후 곱셈 연산자 추가해야함
                    if (str.charAt(i) == ' ') {
                        int tmp = Integer.parseInt(modi.toString());
                        modi = new StringBuilder();
                        modi.append(Integer.toString(tmp));
                        modi.append("*");
                    }
                    // ! 등장시 사이에 있는 변수 또는 정수 출력시킴
                    if (str.charAt(i) == '!') {
                        if (u_cont == 1) {
                            //어 일때
                            writer.write("  printf(\"%d\", _input);\n");
                        }
                        //어어 일때
                        if (u_cont == 2) {
                            writer.write("  printf(\"%d\", _input2);\n");
                        }
                        //어어어 일때
                        if (u_cont == 3) {
                            writer.write("  printf(\"%d\", _input3);\n");
                        }
                        String st = modi.toString();
                        // 만약 그 외의 정수형일때
                        if (!st.equals("")) {
                            writer.write("int tmp = " + st + ";\nprintf(\"%d\",tmp);\n");
                        }
                    }
                }
            }
            String st="";
            // 문자형 출력시 (유니코드)
           if (str.contains("식") && str.contains("ㅋ")) {
                for (int i = 0; i < str.length(); i++) {
                    //변수 특정을 위해 '어' 카운트하기
                    if (str.charAt(i) == '어') {
                        u_cont++;
                    }
                    // . 이 등장하면 num +1
                    if (str.charAt(i) == '.') {
                        num += 1;
                    }
                    // , 이 등장하면 -1
                    if (str.charAt(i) == ',') {
                        num -= 1;
                    }
                    //공백 등장 시 현재 값 저장 후 곱셈 연산자 추가
                    if (str.charAt(i) == ' ') {

                        st += Integer.toString(num);
                        num = 0;
                        st += "*";

                    }
                    // ㅋ 등장 시 문자형 출력
                    if (str.charAt(i) == 'ㅋ') {
                       if(u_cont == 0){
                           // 변수 출력 아닐 때
                           st += Integer.toString(num);
                           writer.write("  printf(\"%c\", "+st+");\n");
                       }
                       //어 출력
                        if (u_cont == 1) {
                            writer.write("  printf(\"%c\", _input);\n");
                            break;
                        }
                        // 어어 출력
                        else if (u_cont == 2) {
                            writer.write("  printf(\"%c\", _input2);\n");
                            break;
                        }
                        //어어어 출력
                        else if (u_cont == 3) {
                            writer.write("  printf(\"%c\", _input3);\n");
                            break;
                        }


                    }
                }

            }
            //엄이 들어가 있을 때
            if (str.contains("엄")) {
                //대입을 위해
                for (int i = 0; i < str.length(); i++) {
                    //만약 "어" 라면 변수 호출을 위해 몇번째 변수인지 확인이 필요함.
                    // 엄 -> 첫번쨰 변수에 대입 / 어엄 -> 두번째 변수대임 / 어어엄 -> 3번째 변수에 대임
                    // 대입 전 어
                    if (str.charAt(i) == '어') {
                        ucnt++;
                    }
                    if (str.charAt(i) == '엄') {
                        //"엄" 은 대입 / 만약 "엄"이 첫 등장이면 초괴화 필요하다.
                        //대입 전 "어"인지 대입 후 "어"인지 구별위한 변수 daeib

                        //식? 등장 시 입력받아 변수에 대입하기
                        if (str.contains("식?")) {
                            // 만약 식? 등장 시 식플래그를 true 로 바꿔줌
                            sik_flag = true;
                            // "엄식?"
                            if (ucnt == 0) {
                                umm_flag = true;
                                writer.write("int _input;\nprintf(\"input: \");\n");
                                writer.write("scanf(\"%d\", &_input);\n");
                                break;
                            }
                            // 어엄식?
                            if (ucnt == 1) {
                                uumm_flag = true;
                                writer.write("int _input2;\nprintf(\"input: \");\n");
                                writer.write("scanf(\"%d\", &_input2);\n");
                                break;
                            }
                            // 어어엄식?
                            if (ucnt == 2) {
                                uuumm_flag = true;
                                writer.write("int _input3;printf(\"input: \");\n");
                                writer.write("scanf(\"%d\", &_input3);\n");
                                break;
                            }
                            break;
                        }
                        //대입전일때
                        if(!daeib){
                        switch (ucnt) {
                            //엄일때
                            case 0:
                                if (!umm_flag) {
                                    writer.write("  int _input;\n  _input=");
                                    umm_flag = true;
                                    break;
                                } else {
                                    writer.write("  _input=");
                                    break;
                                }
                                //어엄일때
                            case 1:
                                if (!uumm_flag) {
                                    writer.write("  int _input2;\n _input2=");
                                    uumm_flag = true;
                                    break;
                                } else {
                                    writer.write("  _input2=");
                                    break;
                                }
                                //어어엄일때
                            case 2:
                                if (!uuumm_flag) {
                                    writer.write("  int _input3;\n _input3=");
                                    uuumm_flag = true;
                                    break;
                                } else {
                                    writer.write("  _input3=");
                                    break;
                                }
                        }
                        ucnt = 0;
                        daeib = true;

                    }}
                    if (daeib && str.charAt(i) == '어') {
                        switch (ucnt) {
                            //엄어일때
                            case 1:
                                writer.write(" _input");
                                break;
                                //엄어어일때
                            case 2:
                                writer.write(" _input2");
                                break;
                                //엄어어어일때
                            case 3:
                                writer.write(" _input3");
                                break;
                        }
                    }
                    if (daeib && str.charAt(i) == '.') {
                        num += 1;
                    }
                    if (daeib && str.charAt(i) == ',') {
                        num -= 1;
                    }
                    if (daeib && str.charAt(i) == ' ') {

                        writer.write(Integer.toString(num));
                        num = 0;
                        writer.write("*");

                    }


                }
                if(!sik_flag) {
                    if(num>=0){
                        writer.write("+"+Integer.toString(num));
                    }else {
                        writer.write(Integer.toString(num));
                    }
                    writer.write(";");
                }
                // 5. BufferedWriter close


            }
            writer.write("\n");
        }
        writer.close();

        reader.close();
    }
}
