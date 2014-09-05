package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Otaria")
@Types({Type.PLANE})
@SubTypes({SubType.DOMINARIA})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Otaria extends Card
{
	public static final class OtariaGivesFlashback extends StaticAbility
	{
		public OtariaGivesFlashback(GameState state)
		{
			super(state, "Instant and sorcery cards in graveyards have flashback. The flashback cost is equal to the card's mana cost.");

			SetGenerator affected = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(Players.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.GRANT_COSTED_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flashback.class));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, affected);
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class Primer extends EventTriggeredAbility
	{
		public Primer(GameState state)
		{
			super(state, "Whenever you roll (C), take an extra turn after this one.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Otaria(GameState state)
	{
		super(state);

		this.addAbility(new OtariaGivesFlashback(state));

		this.addAbility(new Primer(state));
	}
}
