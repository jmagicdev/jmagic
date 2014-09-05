package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Riftsweeper")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Riftsweeper extends Card
{
	public static final class RiftsweeperAbility0 extends EventTriggeredAbility
	{
		public RiftsweeperAbility0(GameState state)
		{
			super(state, "When Riftsweeper enters the battlefield, choose target face-up exiled card. Its owner shuffles it into his or her library.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(InZone.instance(ExileZone.instance()), Cards.instance(), FaceUp.instance()), "target face-up exiled card");

			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Target face-up exiled card's owner shuffles it into his or her library.");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(targetedBy(target), OwnerOf.instance(targetedBy(target))));
			this.addEffect(shuffle);
		}
	}

	public Riftsweeper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Riftsweeper enters the battlefield, choose target face-up exiled
		// card. Its owner shuffles it into his or her library.
		this.addAbility(new RiftsweeperAbility0(state));
	}
}
