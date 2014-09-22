package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Odunos River Trawler")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OdunosRiverTrawler extends Card
{
	public static final class OdunosRiverTrawlerAbility0 extends EventTriggeredAbility
	{
		public OdunosRiverTrawlerAbility0(GameState state)
		{
			super(state, "When Odunos River Trawler enters the battlefield, return target enchantment creature card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.ENCHANTMENT), HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target enchantment creature card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target enchantment creature card from your graveyard to your hand."));
		}
	}

	public static final class OdunosRiverTrawlerAbility1 extends ActivatedAbility
	{
		public OdunosRiverTrawlerAbility1(GameState state)
		{
			super(state, "(W), Sacrifice Odunos River Trawler: Return target enchantment creature card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(W)"));
			this.addCost(sacrificeThis("Odunos River Trawler"));

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.ENCHANTMENT), HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target enchantment creature card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target enchantment creature card from your graveyard to your hand."));
		}
	}

	public OdunosRiverTrawler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Odunos River Trawler enters the battlefield, return target
		// enchantment creature card from your graveyard to your hand.
		this.addAbility(new OdunosRiverTrawlerAbility0(state));

		// (W), Sacrifice Odunos River Trawler: Return target enchantment
		// creature card from your graveyard to your hand.
		this.addAbility(new OdunosRiverTrawlerAbility1(state));
	}
}
