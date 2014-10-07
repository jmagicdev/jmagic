package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hydra Broodmaster")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class HydraBroodmaster extends Card
{
	public static final class HydraBroodmasterAbility0 extends ActivatedAbility
	{
		public HydraBroodmasterAbility0(GameState state)
		{
			super(state, "(X)(X)(G): Monstrosity X.");
			this.setManaCost(new ManaPool("(X)(X)(G)"));

			state.ensureTracker(new org.rnd.jmagic.engine.eventTypes.Monstrosity.MonstrousTracker());
			EventFactory monstrosity = new EventFactory(EventType.MONSTROSITY, "Monstrosity X.");
			monstrosity.parameters.put(EventType.Parameter.CAUSE, This.instance());
			monstrosity.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			monstrosity.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
			this.addEffect(monstrosity);

			monstrosity.setLink(this);
		}
	}

	public static final class HydraBroodmasterAbility1 extends EventTriggeredAbility
	{
		public HydraBroodmasterAbility1(GameState state)
		{
			super(state, "When Hydra Broodmaster becomes monstrous, put X X/X green Hydra creature tokens onto the battlefield.");
			this.addPattern(whenThisBecomesMonstrous());

			SetGenerator X = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.NUMBER);

			CreateTokensFactory hydras = new CreateTokensFactory(X, X, X, "Put X X/X green Hydra creature tokens onto the battlefield.");
			hydras.setColors(Color.GREEN);
			hydras.setSubTypes(SubType.HYDRA);
			this.addEffect(hydras.getEventFactory());
		}
	}

	public HydraBroodmaster(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// (X)(X)(G): Monstrosity X. (If this creature isn't monstrous, put X
		// +1/+1 counters on it and it becomes monstrous.)
		this.addAbility(new HydraBroodmasterAbility0(state));

		// When Hydra Broodmaster becomes monstrous, put X X/X green Hydra
		// creature tokens onto the battlefield.
		this.addAbility(new HydraBroodmasterAbility1(state));
	}
}
