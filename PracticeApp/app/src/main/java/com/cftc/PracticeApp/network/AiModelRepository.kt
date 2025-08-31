package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.AiModel
import com.cftc.PracticeApp.model.ModelCategory
import com.cftc.PracticeApp.model.ModelStatus
import kotlinx.coroutines.delay

class AiModelRepository() {
    suspend fun getAiModelsList(): List<AiModel> {
        delay(1000)
        return listOf(
            AiModel(
                id = "1",
                name = "GPT-4 Vision Pro",
                imageUrl = "https://images.unsplash.com/photo-1717143587138-2532a35ce9b2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhcnRpZmljaWFsJTIwaW50ZWxsaWdlbmNlJTIwbG9nb3xlbnwxfHx8fDE3NTY2MTI1MDF8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Online,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "2",
                name = "Claude 3.5 Sonnet Ultra Advanced Language Model",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Online,
                modelCategory = ModelCategory.Language
            ),
            AiModel(
                id = "3",
                name = "Whisper Large",
                imageUrl = null, // missing image
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Audio
            ),
            AiModel(
                id = "4",
                name = "LLaMA 3.1 Turbo",
                imageUrl = "https://images.unsplash.com/photo-1744339699989-550c61f3ecb8?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzb2Z0d2FyZSUyMGRldmVsb3BtZW50JTIwaWNvbnxlbnwxfHx8fDE3NTY1NDQ4MDJ8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Online,
                modelCategory = ModelCategory.Language
            ),
            AiModel(
                id = "5",
                name = "DALL-E 3",
                imageUrl = "https://images.unsplash.com/photo-1647356191320-d7a1f80ca777?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxuZXVyYWwlMjBuZXR3b3JrJTIwdmlzdWFsaXphdGlvbnxlbnwxfHx8fDE3NTY1ODQ3ODd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "6",
                name = "Gemini Pro",
                imageUrl = "https://images.unsplash.com/photo-1554744512-d6c603f27c54?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtYWNoaW5lJTIwbGVhcm5pbmclMjBtb2RlbHxlbnwxfHx8fDE3NTY2MTI1MDR8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Online,
                modelCategory = ModelCategory.Language
            ),
            AiModel(
                id = "7",
                name = "ElevenLabs Voice AI Premium Enterprise Solution",
                imageUrl = "https://images.unsplash.com/photo-1717143587138-2532a35ce9b2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhcnRpZmljaWFsJTIwaW50ZWxsaWdlbmNlJTIwbG9nb3xlbnwxfHx8fDE3NTY2MTI1MDF8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Online,
                modelCategory = ModelCategory.Audio
            ),
            AiModel(
                id = "8",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "932",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "93435243",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "93",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "93234",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "944",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "9123",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "9777",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "9256",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "97",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "90",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "91235",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            ),
            AiModel(
                id = "9521",
                name = "Anthropic Claude Vision Multi-Modal Analysis Engine",
                imageUrl = "https://images.unsplash.com/photo-1661347998423-b15d37d6f61e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNoJTIwY29tcGFueSUyMGxvZ298ZW58MXx8fHwxNzU2NTA1NDUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                status = ModelStatus.Offline,
                modelCategory = ModelCategory.Vision
            )
        )
    }
}