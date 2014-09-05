package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Bident of Thassa")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT,Type.ENCHANTMENT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class BidentofThassa extends Card
{
	public static final class BidentofThassaAbility0 extends EventTriggeredAbility
	{
		public BidentofThassaAbility0(GameState state)
		{
			super(state, "Whenever a creature you control deals combat damage to a player, you may draw a card.");

			this.addPattern(new SimpleDamagePattern(CREATURES_YOU_CONTROL, Players.instance(), true));
			this.addEffect(youMay(drawACard()));
		}
	}

	public static final class BidentofThassaAbility1 extends ActivatedAbility
	{
		public BidentofThassaAbility1(GameState state)
		{
			super(state, "(1)(U), (T): Creatures your opponents control attack this turn if able.");
			this.setManaCost(new ManaPool("1U"));
			this.costsTap = true;

			SetGenerator requirement = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, Identity.instance(requirement));
			this.addEffect(createFloatingEffect("Creatures your opponents control attack this turn if able.", part));
		}
	}

	public BidentofThassa(GameState state)
	{
		super(state);


		// Whenever a creature you control deals combat damage to a player, you may draw a card.
		this.addAbility(new BidentofThassaAbility0(state));

		// {1}{U}, {T}: Creatures your opponents control attack this turn if able.
		this.addAbility(new BidentofThassaAbility1(state));
	}
}
