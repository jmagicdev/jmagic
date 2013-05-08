package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zulaport Enforcer")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ZulaportEnforcer extends Card
{
	public static final class ZulaportEnforcerAbility5 extends StaticAbility
	{
		public ZulaportEnforcerAbility5(GameState state)
		{
			super(state, "Zulaport Enforcer can't be blocked except by black creatures.");

			SetGenerator sharesColor = HasColor.instance(Color.BLACK);
			SetGenerator creatures = HasType.instance(Type.CREATURE);
			SetGenerator cantBlockThis = RelativeComplement.instance(creatures, sharesColor);
			SetGenerator illegalBlock = Intersect.instance(Blocking.instance(This.instance()), cantBlockThis);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
			this.addEffectPart(part);
		}
	}

	public ZulaportEnforcer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Level up (4) ((4): Put a level counter on this. Level up only as a
		// sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(4)"));

		// LEVEL 1-2, 3/3
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 2, 3, 3));

		// LEVEL 3+, 5/5
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 3, 5, 5, "Can't be blocked except by black creatures", ZulaportEnforcerAbility5.class));
	}
}
