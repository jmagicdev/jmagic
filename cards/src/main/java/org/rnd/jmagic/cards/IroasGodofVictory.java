package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Iroas, God of Victory")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("2RW")
@ColorIdentity({Color.RED, Color.WHITE})
public final class IroasGodofVictory extends Card
{
	public static final class IroasGodofVictoryAbility1 extends StaticAbility
	{
		public IroasGodofVictoryAbility1(GameState state)
		{
			super(state, "As long as your devotion to red and white is less than seven, Iroas isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.RED, Color.WHITE));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class IroasGodofVictoryAbility2 extends StaticAbility
	{
		public IroasGodofVictoryAbility2(GameState state)
		{
			super(state, "Creatures you control can't be blocked except by two or more creatures.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(TerrorofKruinPass.KRUIN_BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addEffectPart(part);
		}
	}

	public static final class IroasGodofVictoryAbility3 extends StaticAbility
	{
		public IroasGodofVictoryAbility3(GameState state)
		{
			super(state, "Prevent all damage that would be dealt to attacking creatures you control.");

			SetGenerator yourAttackers = Intersect.instance(ControlledBy.instance(You.instance()), Attacking.instance());
			DamageReplacementEffect prevent = new org.rnd.jmagic.abilities.PreventAllTo(state.game, yourAttackers, "Prevent all damage that would be dealt to attacking creatures you control.");
			this.addEffectPart(replacementEffectPart(prevent));
		}
	}

	public IroasGodofVictory(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(4);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to red and white is less than seven, Iroas
		// isn't a creature.
		this.addAbility(new IroasGodofVictoryAbility1(state));

		// Creatures you control can't be blocked except by two or more
		// creatures.
		this.addAbility(new IroasGodofVictoryAbility2(state));

		// Prevent all damage that would be dealt to attacking creatures you
		// control.
		this.addAbility(new IroasGodofVictoryAbility3(state));
	}
}
