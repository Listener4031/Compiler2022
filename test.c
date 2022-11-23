int c;
int c1 = 7;

int func(int a, int b){
    if(a > b) return 1;
    else return 0;
}

void func1(int p){
    p = p + 1;
}

int main(){
    int a = 10;
    int b = 6;
    c = 7;
    return func(a + b, c);
}
