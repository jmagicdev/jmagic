package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Retraction Helix")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class RetractionHelix extends Card
{
	public static final class BounceStuff extends ActivatedAbility
	{
		public BounceStuff(GameState state)
		{
			super(state, "(T): Return target nonland permanent to its owner's hand.");
			this.costsTap = true;

			SetGenerator nonlands = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			SetGenerator target = targetedBy(this.addTarget(nonlands, "target nonland permanent"));
			this.addEffect(bounce(target, "Return target nonland permanent to its owner's hand."));
		}
	}

	public RetractionHelix(GameState state)
	{
		super(state);

		// Until end of turn, target creature gains
		// "(T): Return target nonland permanent to its owner's hand."
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(addAbilityUntilEndOfTurn(target, BounceStuff.class, "Until end of turn, target creature gains \"(T): Return target nonland permanent to its owner's hand.\""));
	}
}
