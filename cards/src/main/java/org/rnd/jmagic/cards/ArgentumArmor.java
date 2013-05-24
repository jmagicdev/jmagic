package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Argentum Armor")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class ArgentumArmor extends Card
{
	public static final class ArgentumArmorAbility0 extends StaticAbility
	{
		public ArgentumArmorAbility0(GameState state)
		{
			super(state, "Equipped creature gets +6/+6.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +6, +6));
		}
	}

	public static final class ArgentumArmorAbility1 extends EventTriggeredAbility
	{
		public ArgentumArmorAbility1(GameState state)
		{
			super(state, "Whenever equipped creature attacks, destroy target permanent.");
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, EquippedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(destroy(target, "Destroy target permanent."));
		}
	}

	public ArgentumArmor(GameState state)
	{
		super(state);

		// Equipped creature gets +6/+6.
		this.addAbility(new ArgentumArmorAbility0(state));

		// Whenever equipped creature attacks, destroy target permanent.
		this.addAbility(new ArgentumArmorAbility1(state));

		// Equip (6)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(6)"));
	}
}
