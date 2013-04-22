package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eldrazi Monument")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class EldraziMonument extends Card
{
	public static final class Pump extends StaticAbility
	{
		public Pump(GameState state)
		{
			super(state, "Creatures you control get +1/+1, have flying, and are indestructible.");

			SetGenerator yourCreatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()));

			this.addEffectPart(modifyPowerAndToughness(yourCreatures, 1, 1));

			this.addEffectPart(addAbilityToObject(yourCreatures, org.rnd.jmagic.abilities.keywords.Flying.class));

			this.addEffectPart(indestructible(yourCreatures));
		}
	}

	public static final class AppeaseEldrazi extends EventTriggeredAbility
	{
		public AppeaseEldrazi(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a creature. If you can't, sacrifice Eldrazi Monument.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Sacrifice a creature. If you can't, sacrifice Eldrazi Monument.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(sacrificeACreature()));
			effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(sacrificeThis("Eldrazi Monument")));
			this.addEffect(effect);
		}
	}

	public EldraziMonument(GameState state)
	{
		super(state);

		// Creatures you control get +1/+1, have flying, and are indestructible.
		this.addAbility(new Pump(state));

		// At the beginning of your upkeep, sacrifice a creature. If you can't,
		// sacrifice Eldrazi Monument.
		this.addAbility(new AppeaseEldrazi(state));
	}
}
