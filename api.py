from fastapi import FastAPI

app = FastAPI()

# 예시 사용자 데이터
users = {
    1: {"name": "Alice", "age": 25},
    2: {"name": "Bob", "age": 30}
}

# 비동기 사용자 조회 엔드포인트
@app.get("/users/{user_id}")
async def get_user(user_id: int):
    user = users.get(user_id)
    if user:
        return user
    return {"error": "User not found"}

# 기본 엔드포인트
@app.get("/")
async def root():
    return {"message": "Hello, FastAPI!"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("api:app", host="127.0.0.1", port=8000, reload=True)