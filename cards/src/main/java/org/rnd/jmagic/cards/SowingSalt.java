package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sowing Salt")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class SowingSalt extends Card
{
	public SowingSalt(GameState state)
	{
		super(state);

		// Exile target nonbasic land.
		Target t = this.addTarget(RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), "target nonbasic land");
		this.addEffect(exile(targetedBy(t), "Exile target nonbasic land."));

		// Search its owner's graveyard, hand, and library for all cards with
		// the same name as that card and exile them.
		SetGenerator itsOwner = OwnerOf.instance(targetedBy(t));
		SetGenerator graveyard = GraveyardOf.instance(itsOwner);
		SetGenerator hand = HandOf.instance(itsOwner);
		SetGenerator library = LibraryOf.instance(itsOwner);
		SetGenerator zones = Union.instance(graveyard, hand, library);

		EventFactory effect = new EventFactory(EventType.SEARCH_FOR_ALL_AND_PUT_INTO, "Search its owner's graveyard, hand, and library for all cards with the same name as that card and exile them.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.ZONE, zones);
		effect.parameters.put(EventType.Parameter.TYPE, HasName.instance(NameOf.instance(targetedBy(t))));
		effect.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(effect);

		// Then that player shuffles his or her library.
		this.addEffect(shuffleLibrary(targetedBy(t), "Then that player shuffles his or her library."));
	}
}
