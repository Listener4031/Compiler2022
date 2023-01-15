@a = global i32 21
@b = global i32 0
@c = global i8 1
@d = global i8 0
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
  %0 = load i32, i32* @a
  %1 = add i32 32, %0
  store i32 %2, i32* @b
  %3 = load i32, i32* @b
  %4 = load i32, i32* @a
  %5 = icmp sgt i1 %3, %4
  %6 = zext i1 %5 to i8
  store i8 %6, i8* @d
  br label %_global_return

_global_return:
  ret void
}
define i32 @main() {
main_allocate:
  %0 = alloca i32
  br label %main_entry

main_entry:
  store i32 0, i32* %0
  store i32 1, i32* %0
  br label %main_return
  br label %main_return

main_return:
  %1 = load i32, i32* %0
  ret i32 %1
}
