package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Energy Flux")
@Types({Type.ENCHANTMENT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class EnergyFlux extends Card
{
	public static final class ThisArtifactKindOfSucksNow extends EventTriggeredAbility
	{
		public ThisArtifactKindOfSucksNow(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice this artifact unless you pay (2).");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacrifice = sacrificeThis("this artifact");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (2)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));
			pay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(unless(You.instance(), sacrifice, pay, "Sacrifice this artifact unless you pay (2)."));
		}
	}

	public static final class EnergyFluxAbility0 extends StaticAbility
	{
		public EnergyFluxAbility0(GameState state)
		{
			super(state, "All artifacts have \"At the beginning of your upkeep, sacrifice this artifact unless you pay (2).\"");

			this.addEffectPart(addAbilityToObject(ArtifactPermanents.instance(), ThisArtifactKindOfSucksNow.class));
		}
	}

	public EnergyFlux(GameState state)
	{
		super(state);

		// All artifacts have
		// "At the beginning of your upkeep, sacrifice this artifact unless you pay (2)."
		this.addAbility(new EnergyFluxAbility0(state));
	}
}
