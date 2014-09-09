package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Draco")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("(16)")
@ColorIdentity({})
public final class Draco extends Card
{
	public static final class DracoAbility0 extends StaticAbility
	{
		public DracoAbility0(GameState state)
		{
			super(state, "Draco costs (2) less to cast for each basic land type among lands you control.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, Domain.instance(You.instance()));
			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class DracoAbility2 extends EventTriggeredAbility
	{
		public DracoAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Draco unless you pay (10). This cost is reduced by (2) for each basic land type among lands you control.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory payMana = new EventFactory(EventType.PAY_MANA, "Pay (10). This cost is reduced by (2) for each basic land type among lands you control.");
			payMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payMana.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			payMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			payMana.parameters.put(EventType.Parameter.NUMBER, Subtract.instance(numberGenerator(5), Domain.instance(You.instance())));
			this.addEffect(unless(You.instance(), sacrificeThis("Draco"), payMana, "Sacrifice Draco unless you pay (10). This cost is reduced by (2) for each basic land type among lands you control."));
		}
	}

	public Draco(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		// Domain \u2014 Draco costs (2) less to cast for each basic land type
		// among lands you control.
		this.addAbility(new DracoAbility0(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Domain \u2014 At the beginning of your upkeep, sacrifice Draco unless
		// you pay (10). This cost is reduced by (2) for each basic land type
		// among lands you control.
		this.addAbility(new DracoAbility2(state));
	}
}
