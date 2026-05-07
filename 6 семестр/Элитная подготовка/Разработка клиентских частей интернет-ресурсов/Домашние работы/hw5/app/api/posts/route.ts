import { NextResponse } from 'next/server'
import { posts } from '@/data/posts'

export async function GET() {
    return NextResponse.json(posts)
}
