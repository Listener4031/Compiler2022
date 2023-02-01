@g1 = global i32 1
@g2 = global i8 0
define void @print(i8* %0)
define void @println(i8* %0)
define void @printInt(i32 %0)
define void @printlnInt(i32 %0)
define i8* @getString()
define i32 @getInt()
define i8* @toString(i32 %0)
define i32 @length(i8* %0)
define i8* @substring(i8* %0, i32 %1, i32 %2)
define i32 @parseInt(i8* %0)
define i32 @ord(i8* %0)
define void @_global() {
_global_allocate:
  br label %_global_entry

_global_entry:
  br label %_global_return

_global_return:
  ret void
}

define i8 @func1(i32 %0, i32 %1) {
func1_allocate:
  %2 = alloca i32
  %3 = alloca i32
  %4 = alloca i8
  br label %func1_entry

func1_entry:
  store i32 %0, i32* %2
  store i32 %1, i32* %3
  %5 = load i32, i32* %2
  %6 = load i32, i32* %3
  %7 = add i32 %5, %6
  %8 = icmp sgt i1 %7, 20
  br i1 %9, label %func1_9_true, label %func1_9_false

func1_13_true:
  store i8 0, i8* %4
  br label %func1_return
  br label %func1_13_out

func1_13_false:
  store i8 1, i8* %4
  br label %func1_return
  br label %func1_13_out

func1_13_out:
  br label %func1_9_out

func1_9_true:
  store i8 1, i8* %4
  br label %func1_return
  br label %func1_9_out

func1_9_false:
  %10 = load i32, i32* %2
  %11 = load i32, i32* %3
  %12 = icmp sge i1 %10, %11
  br i1 %13, label %func1_13_true, label %func1_13_false

func1_9_out:
  br label %func1_return

func1_return:
  %14 = load i8, i8* %4
  ret i8 %14
}

define i32 @main() {
main_allocate:
  %0 = alloca i32
  %1 = alloca i32
  %2 = alloca i8
  br label %main_entry

main_entry:
  store i32 0, i32* %0
  store i32 1, i32* %1
  store i8 1, i8* %2
  store i32 0, i32* %0
  br label %main_return
  br label %main_return

main_return:
  %3 = load i32, i32* %0
  ret i32 %3
}
