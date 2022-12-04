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
define i32 @main() {
main_allocate:
  %0 = alloca i32
  %1 = alloca i32
  br label %main_entry

main_entry:
  store i32 0, i32* %0
  store i32 2, i32* %1
  br label %main_1_condition

main_1_condition:
  %2 = load i32, i32* %1
  %3 = icmp slt i1 %2, 10
  br i1 %4, label %main_4_body, label %main_4_out

main_4_body:
  %5 = load i32, i32* %1
  %6 = add i32 %5, 1
  store i32 %7, i32* %1
  br label %main_1_condition

main_4_out:
  store i32 3, i32* %0
  br label %main_return
  br label %main_return

main_return:
  %8 = load i32, i32* %0
  ret i32 %8
}
