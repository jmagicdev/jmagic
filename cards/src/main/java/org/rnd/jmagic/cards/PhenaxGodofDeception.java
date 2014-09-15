package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phenax, God of Deception")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class PhenaxGodofDeception extends Card
{
	public static final class PhenaxGodofDeceptionAbility1 extends StaticAbility
	{
		public PhenaxGodofDeceptionAbility1(GameState state)
		{
			super(state, "As long as your devotion to blue and black is less than seven, Phenax isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.BLUE, Color.BLACK));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class MillStuff extends ActivatedAbility
	{
		public MillStuff(GameState state)
		{
			super(state, "(T): Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, ToughnessOf.instance(ABILITY_SOURCE_OF_THIS), "Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness."));
		}
	}

	public static final class PhenaxGodofDeceptionAbility2 extends StaticAbility
	{
		public PhenaxGodofDeceptionAbility2(GameState state)
		{
			super(state, "Creatures you control have \"(T): Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness.\"");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, MillStuff.class));
		}
	}

	public PhenaxGodofDeception(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(7);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to blue and black is less than seven, Phenax
		// isn't a creature.
		this.addAbility(new PhenaxGodofDeceptionAbility1(state));

		// Creatures you control have
		// "(T): Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness."
		this.addAbility(new PhenaxGodofDeceptionAbility2(state));
	}
}
