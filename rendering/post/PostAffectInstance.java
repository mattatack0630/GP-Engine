package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/16/2016.
 */
public abstract class PostAffectInstance
{
	/**
	 * Apply an affect to a screen.
	 * Affect has one input, the post processors current screen.
	 * It modifies the screen directly.
	 */
	public abstract FboObject callAffect(FboObject currentScreen);
}
