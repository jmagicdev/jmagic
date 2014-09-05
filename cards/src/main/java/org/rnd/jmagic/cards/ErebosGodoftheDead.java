package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Erebos, God of the Dead")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class ErebosGodoftheDead extends Card
{
	public static final class ErebosGodoftheDeadAbility1 extends StaticAbility
	{
		public ErebosGodoftheDeadAbility1(GameState state)
		{
			super(state, "As long as your devotion to black is less than five, Erebos isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 4), DevotionTo.instance(Color.BLACK));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class ErebosGodoftheDeadAbility2 extends StaticAbility
	{
		public ErebosGodoftheDeadAbility2(GameState state)
		{
			super(state, "Your opponents can't gain life.");

			SimpleEventPattern gainPattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
			gainPattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(gainPattern));
			this.addEffectPart(part);
		}
	}

	public static final class ErebosGodoftheDeadAbility3 extends ActivatedAbility
	{
		public ErebosGodoftheDeadAbility3(GameState state)
		{
			super(state, "(1)(B), Pay 2 life: Draw a card.");
			this.setManaCost(new ManaPool("1B"));
			this.addCost(payLife(You.instance(), 2, "Pay 2 life"));
			this.addEffect(drawACard());
		}
	}

	public ErebosGodoftheDead(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(7);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to black is less than five, Erebos isn't a
		// creature. (Each {B} in the mana costs of permanents you control
		// counts toward your devotion to black.)
		this.addAbility(new ErebosGodoftheDeadAbility1(state));

		// Your opponents can't gain life.
		this.addAbility(new ErebosGodoftheDeadAbility2(state));

		// {1}{B}, Pay 2 life: Draw a card.
		this.addAbility(new ErebosGodoftheDeadAbility3(state));
	}
}
