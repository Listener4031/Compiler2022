int c = 5;

int func(int a, int b){
    if(a > b) return 1;
    else return 0;
}

int main(){
    int a = 10;
    int b = 6;
    return func(a + b, c);
}
