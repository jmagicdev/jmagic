package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magnetic Mine")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class MagneticMine extends Card
{
	public static final class MagneticMineAbility0 extends EventTriggeredAbility
	{
		public MagneticMineAbility0(GameState state)
		{
			super(state, "Whenever another artifact is put into a graveyard from the battlefield, Magnetic Mine deals 2 damage to that artifact's controller.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), RelativeComplement.instance(ArtifactPermanents.instance(), ABILITY_SOURCE_OF_THIS), true));

			SetGenerator thatObject = OldObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			this.addEffect(permanentDealDamage(2, ControllerOf.instance(thatObject), "Magnetic Mine deals 2 damage to that artifact's controller."));
		}
	}

	public MagneticMine(GameState state)
	{
		super(state);

		// Whenever another artifact is put into a graveyard from the
		// battlefield, Magnetic Mine deals 2 damage to that artifact's
		// controller.
		this.addAbility(new MagneticMineAbility0(state));
	}
}
