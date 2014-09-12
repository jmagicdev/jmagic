package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Avarice Amulet")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@ColorIdentity({})
public final class AvariceAmulet extends Card
{
	public static final class UpkeepDraw extends EventTriggeredAbility
	{
		public UpkeepDraw(GameState state)
		{
			super(state, "At the beginning of your upkeep, draw a card.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(drawACard());
		}
	}

	public static final class AvariceAmuletAbility0 extends StaticAbility
	{
		public AvariceAmuletAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+0 and has vigilance and \"At the beginning of your upkeep, draw a card.\"");
			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +0));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Vigilance.class, UpkeepDraw.class));
		}
	}

	public static final class AvariceAmuletAbility1 extends EventTriggeredAbility
	{
		public AvariceAmuletAbility1(GameState state)
		{
			super(state, "When equipped creature dies, target opponent gains control of Avarice Amulet.");

			ZoneChangePattern equippedDies = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), EquippedBy.instance(ABILITY_SOURCE_OF_THIS), true);
			this.addPattern(equippedDies);

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, target);
			this.addEffect(createFloatingEffect(Empty.instance(), "Target opponent gains control of Avarice Amulet.", part));
		}
	}

	public AvariceAmulet(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+0 and has vigilance and
		// "At the beginning of your upkeep, draw a card."
		this.addAbility(new AvariceAmuletAbility0(state));

		// When equipped creature dies, target opponent gains control of Avarice
		// Amulet.
		this.addAbility(new AvariceAmuletAbility1(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
