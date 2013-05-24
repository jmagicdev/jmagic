package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Luminarch Ascension")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class LuminarchAscension extends Card
{
	public static final class Quest extends EventTriggeredAbility
	{
		public Quest(GameState state)
		{
			super(state, "At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, EndStepOf.instance(OpponentsOf.instance(You.instance())));
			this.addPattern(pattern);

			state.ensureTracker(new LifeLostThisTurn.LifeTracker());
			this.interveningIf = Intersect.instance(Sum.instance(LifeLostThisTurn.instance(You.instance())), numberGenerator(0));

			this.addEffect(youMayPutAQuestCounterOnThis("Luminarch Ascension"));
		}
	}

	public static final class Treasure extends ActivatedAbility
	{
		public Treasure(GameState state)
		{
			super(state, "(1)(W): Put a 4/4 white Angel creature token with flying onto the battlefield. Activate this ability only if Luminarch Ascension has four or more quest counters on it.");

			this.setManaCost(new ManaPool("1W"));

			this.addActivateRestriction(Intersect.instance(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.QUEST)), Between.instance(null, 3)));

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 white Angel creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.ANGEL);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public LuminarchAscension(GameState state)
	{
		super(state);

		this.addAbility(new Quest(state));

		this.addAbility(new Treasure(state));
	}
}
