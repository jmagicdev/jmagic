package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mindless Null")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MindlessNull extends Card
{
	public static final class CantBlock extends StaticAbility
	{
		public CantBlock(GameState state)
		{
			super(state, "Mindless Null can't block unless you control a Vampire.");

			SetGenerator thisIsBlocking = Intersect.instance(Blocking.instance(), This.instance());
			SetGenerator youDontControlAVampire = Not.instance(Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Both.instance(thisIsBlocking, youDontControlAVampire)));
			this.addEffectPart(part);
		}
	}

	public MindlessNull(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Mindless Null can't block unless you control a Vampire.
		this.addAbility(new CantBlock(state));
	}
}
