@global1 = global i32 0
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
define i32 @main() {
main_allocate:
  %0 = alloca i32
  %1 = alloca i32
  br label %main_entry

main_entry:
  call void @_init()
  store i32 0, i32* %0
  br label %main_return

main_return:
  %2 = load i32, i32* %0
  ret i32 %2
}
