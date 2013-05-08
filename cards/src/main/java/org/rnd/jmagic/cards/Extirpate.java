package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Extirpate")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Extirpate extends Card
{
	public Extirpate(GameState state)
	{
		super(state);

		// Split second
		this.addAbility(new org.rnd.jmagic.abilities.keywords.SplitSecond(state));

		// Choose target card in a graveyard other than a basic land card.
		SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
		SetGenerator basicLands = Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND));
		SetGenerator legalTargets = RelativeComplement.instance(inGraveyards, basicLands);
		Target t = this.addTarget(legalTargets, "target card in a graveyard other than a basic land card");

		// Search its owner's graveyard, hand, and library for all cards with
		// the same name as that card and exile them.
		SetGenerator itsOwner = OwnerOf.instance(targetedBy(t));
		SetGenerator graveyard = GraveyardOf.instance(itsOwner);
		SetGenerator hand = HandOf.instance(itsOwner);
		SetGenerator library = LibraryOf.instance(itsOwner);
		SetGenerator zones = Union.instance(graveyard, hand, library);

		EventFactory effect = new EventFactory(EventType.SEARCH_FOR_ALL_AND_PUT_INTO, "Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for all cards with the same name as that card and exile them.");
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
