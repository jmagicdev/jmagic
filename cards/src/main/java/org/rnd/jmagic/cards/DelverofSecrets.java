package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Delver of Secrets")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
@BackFace(InsectileAberration.class)
public final class DelverofSecrets extends Card
{
	public static final class DelverofSecretsAbility0 extends EventTriggeredAbility
	{
		public DelverofSecretsAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top card of your library.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topCard);
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(look);

			EventFactory reveal = reveal(topCard, "Reveal that card.");
			this.addEffect(ifThen(youMay(reveal, "You may reveal that card."), ifThen(Intersect.instance(EffectResult.instance(reveal), HasType.instance(Type.INSTANT, Type.SORCERY)), transformThis("Delver of Secrets"), "If an instant or sorcery card is revealed this way, transform Delver of Secrets."), "You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets."));

		}
	}

	public DelverofSecrets(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of your upkeep, look at the top card of your
		// library. You may reveal that card. If an instant or sorcery card is
		// revealed this way, transform Delver of Secrets.
		this.addAbility(new DelverofSecretsAbility0(state));
	}
}
