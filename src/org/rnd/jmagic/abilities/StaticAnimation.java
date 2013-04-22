package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

public final class StaticAnimation extends StaticAbility
{
	private Animator animator;

	public StaticAnimation(GameState state, Animator animator, String text)
	{
		super(state, text);
		this.animator = animator;
		this.addEffectPart(animator.getParts());
	}

	@Override
	public StaticAnimation create(Game game)
	{
		return new StaticAnimation(game.physicalState, this.animator, this.getName());
	}
}
