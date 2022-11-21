int c = 1;

int func(int a, int b){
    if(a > b) return 1;
    else return 0;
}

class A{
public:
    int x;
    int y;
    A(int a, int b){
        x = a;
        y = b;
    }
    int F(){
        return x + y;
    }
};

int main(){
    int a = 10;
    int b = 12;
    A aa(a, b);
    return func(aa.F(), c);	
}
