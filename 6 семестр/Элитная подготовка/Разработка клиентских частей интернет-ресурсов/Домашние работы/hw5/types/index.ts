export interface Post {
    id: number
    title: string
    content: string
    tags: string[]
}

export interface Comment {
    id: number
    postId: number
    author: string
    text: string
    image?: string
    createdAt: string
}
