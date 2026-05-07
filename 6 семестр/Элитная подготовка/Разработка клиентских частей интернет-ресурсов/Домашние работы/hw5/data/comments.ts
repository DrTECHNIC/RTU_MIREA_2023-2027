import { Comment } from '@/types'

export let comments: Comment[] = [
    {
        id: 1,
        postId: 1,
        author: 'Иван',
        text: 'Очень понятно объяснено!',
        createdAt: '01.05.2026 12:15:10',
    },
    {
        id: 2,
        postId: 1,
        author: 'Мария',
        text: 'Теперь closures стали намного понятнее',
        createdAt: '01.05.2026 13:48:22',
    },
    {
        id: 3,
        postId: 1,
        author: 'Алексей',
        text: 'Отличный пример использования замыканий',
        createdAt: '02.05.2026 09:12:45',
    },
    {
        id: 4,
        postId: 1,
        author: 'Дмитрий',
        text: 'JavaScript всё ещё удивляет',
        createdAt: '02.05.2026 10:44:01',
    },
    {
        id: 5,
        postId: 1,
        author: 'Ольга',
        text: 'Спасибо за объяснение!',
        createdAt: '02.05.2026 11:17:33',
    },
    {
        id: 6,
        postId: 2,
        author: 'Кирилл',
        text: 'Yield — одна из лучших фишек Python',
        createdAt: '03.05.2026 15:01:50',
    },
    {
        id: 7,
        postId: 2,
        author: 'Анна',
        text: 'Генераторы реально помогают экономить память',
        createdAt: '03.05.2026 17:22:19',
    },
    {
        id: 8,
        postId: 3,
        author: 'Сергей',
        text: 'RAII — гениальная концепция',
        createdAt: '04.05.2026 08:55:02',
    },
]
