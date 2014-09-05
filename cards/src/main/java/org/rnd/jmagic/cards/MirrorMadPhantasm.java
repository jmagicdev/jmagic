package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mirror-Mad Phantasm")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class MirrorMadPhantasm extends Card
{
	public static final class MirrorMadPhantasmAbility1 extends ActivatedAbility
	{
		public MirrorMadPhantasmAbility1(GameState state)
		{
			super(state, "(1)(U): Mirror-Mad Phantasm's owner shuffles it into his or her library. If that player does, he or she reveals cards from the top of that library until a card named Mirror-Mad Phantasm is revealed. The player puts that card onto the battlefield and all other cards revealed this way into his or her graveyard.");
			this.setManaCost(new ManaPool("(1)(U)"));

			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Mirror-Mad Phantasm's owner shuffles it into his or her library.");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(ABILITY_SOURCE_OF_THIS, owner));

			SetGenerator phantasms = HasName.instance("Mirror-Mad Phantasm");
			SetGenerator toReveal = TopMost.instance(LibraryOf.instance(owner), numberGenerator(1), phantasms);

			EventFactory reveal = reveal(toReveal, "He or she reveals cards from the top of that library until a card named Mirror-Mad Phantasm is revealed.");
			EventFactory battlefield = putOntoBattlefield(Intersect.instance(EffectResult.instance(reveal), phantasms), "The player puts that card onto the battlefield.");
			EventFactory graveyard = putIntoGraveyard(RelativeComplement.instance(EffectResult.instance(reveal), phantasms), "The player puts all other cards revealed this way into his or her graveyard.");

			this.addEffect(ifThen(shuffle, sequence(reveal, battlefield, graveyard), "Mirror-Mad Phantasm's owner shuffles it into his or her library. If that player does, he or she reveals cards from the top of that library until a card named Mirror-Mad Phantasm is revealed. The player puts that card onto the battlefield and all other cards revealed this way into his or her graveyard."));
		}
	}

	public MirrorMadPhantasm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(U): Mirror-Mad Phantasm's owner shuffles it into his or her
		// library. If that player does, he or she reveals cards from the top of
		// that library until a card named Mirror-Mad Phantasm is revealed. The
		// player puts that card onto the battlefield and all other cards
		// revealed this way into his or her graveyard.
		this.addAbility(new MirrorMadPhantasmAbility1(state));
	}
}
