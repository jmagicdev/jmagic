package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Marshal's Anthem")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MarshalsAnthem extends Card
{
	public static final class ReturnStuff extends EventTriggeredAbility
	{
		private CostCollection kickerCost;

		public ReturnStuff(GameState state, CostCollection kickerCost)
		{
			super(state, "When Marshal's Anthem enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.");
			this.kickerCost = kickerCost;
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator creaturesInYourYard = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			Target target = this.addTarget(creaturesInYourYard, "up to X target creature cards from your graveyard, where X is the number of times Marshal's Anthem was kicked");
			target.setRange(Between.instance(numberGenerator(0), ThisPermanentWasKicked.instance(kickerCost)));

			EventFactory effect = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(effect);
		}

		@Override
		public ReturnStuff create(Game game)
		{
			return new ReturnStuff(game.physicalState, this.kickerCost);
		}
	}

	public MarshalsAnthem(GameState state)
	{
		super(state);

		// Multikicker (1)(W)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(W)");
		CostCollection kickerCost = kicker.costCollections[0];
		this.addAbility(kicker);

		// Creatures you control get +1/+1.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, CREATURES_YOU_CONTROL, "Creatures you control", +1, +1, true));

		// When Marshal's Anthem enters the battlefield, return up to X target
		// creature cards from your graveyard to the battlefield, where X is the
		// number of times Marshal's Anthem was kicked.
		this.addAbility(new ReturnStuff(state, kickerCost));
	}
}
