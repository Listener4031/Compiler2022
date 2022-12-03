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
define void @func() {
func_allocate:
  %1 = alloca i32
  br label %func_entry

func_entry:
  br label %func_return
  br label %func_return

func_return:
  ret void
}
define i32 @func1() {
func1_allocate:
  %0 = alloca i32
  %1 = alloca i32
  br label %func1_entry

func1_entry:
  store i32 1, i32* %0
  br label %func1_return
  br label %func1_return

func1_return:
  %2 = load i32, i32* %0
  ret i32 %2
}
define i32 @main() {
main_allocate:
  %0 = alloca i32
  %1 = alloca i32
  br label %main_entry

main_entry:
  call void @_init()
  store i32 0, i32* %0
  store i32 2, i32* %0
  br label %main_return
  br label %main_return

main_return:
  %2 = load i32, i32* %0
  ret i32 %2
}
