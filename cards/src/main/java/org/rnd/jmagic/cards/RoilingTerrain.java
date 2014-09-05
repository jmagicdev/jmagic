package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Roiling Terrain")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RoilingTerrain extends Card
{
	public RoilingTerrain(GameState state)
	{
		super(state);

		// Destroy target land,
		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land,"));

		// then Roiling Terrain deals damage to that land's controller equal to
		// the number of land cards in that player's graveyard.
		SetGenerator thatLandsController = ControllerOf.instance(targetedBy(target));
		SetGenerator landsInTheirYard = Intersect.instance(HasType.instance(Type.LAND), InZone.instance(GraveyardOf.instance(thatLandsController)));
		SetGenerator numberOfLands = Count.instance(landsInTheirYard);
		this.addEffect(spellDealDamage(numberOfLands, thatLandsController, "then Roiling Terrain deals damage to that land's controller equal to the number of land cards in that player's graveyard."));
	}
}
